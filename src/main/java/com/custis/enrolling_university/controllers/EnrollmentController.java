package com.custis.enrolling_university.controllers;

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
    public String enroll(@RequestParam Long studentId, @RequestParam Long courseId) {
        return enrollmentService.enrollStudent(studentId, courseId);
    }

    @ExceptionHandler
    public ResponseEntity<String> handlerDataIntegrityViolationException (DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Студент уже записан на данный курс!");
    }

    @GetMapping("/courses")
    public List<Course> getAllCourses() {

        return enrollmentService.getAllCourses();
    }

}

