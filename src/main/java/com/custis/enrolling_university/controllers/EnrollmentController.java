package com.custis.enrolling_university.controllers;

import com.custis.enrolling_university.models.Course;
import com.custis.enrolling_university.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;

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

    @GetMapping("/courses")
    public List<Course> getAllCourses() {

        return enrollmentService.getAllCourses();
    }

}

