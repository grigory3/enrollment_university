package com.custis.enrolling_university.controllers;

import com.custis.enrolling_university.customExceptions.CourseNotFoundException;
import com.custis.enrolling_university.customExceptions.NoFreePlacesException;
import com.custis.enrolling_university.customExceptions.RecordingTimeExpiredException;
import com.custis.enrolling_university.customExceptions.StudentNotFoundException;
import com.custis.enrolling_university.models.Course;

import com.custis.enrolling_university.service.EnrollmentService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<String> enroll(@RequestBody EnrollmentRequest request) {

        if (request.getCourseId() == null || request.getStudentId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("studentId и courseId не могут быть null");
        }

        enrollmentService.enrollStudent(request.getStudentId(), request.getCourseId());

        return ResponseEntity.status(HttpStatus.CREATED).body("Студент успешно зачислен!");
    }

    @ExceptionHandler
    public ResponseEntity<String> handlerDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Студент уже записан на данный курс!");
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundException(CourseNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Курс не найден");
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundException(StudentNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Студент не найден");
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundException(NoFreePlacesException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Нет свободных мест");
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundException(RecordingTimeExpiredException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Окно для записи закрыто");
    }

    @GetMapping("/courses")
    public List<Course> getAllCourses() {

        return enrollmentService.getAllCourses();
    }

}

