package com.custis.enrolling_university.service;

import com.custis.enrolling_university.models.Course;
import com.custis.enrolling_university.models.Enrollment;
import com.custis.enrolling_university.models.Student;
import com.custis.enrolling_university.repositories.CourseRepository;
import com.custis.enrolling_university.repositories.EnrollmentRepository;
import com.custis.enrolling_university.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
public class EnrollmentService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Transactional
    public String enrollStudent(Long studentId, Long courseId) {

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Курс не найден"));

        if (course.getOccupiedSeats() >= course.getTotalSeats()) {
            return "Нет свободных мест";
        }

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Студент не найден"));

        Enrollment enrollment = new Enrollment();

        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollmentRepository.save(enrollment);

        course.setOccupiedSeats(course.getOccupiedSeats() + 1);

        courseRepository.save(course);

        return "Успешно зачислен!";
    }
}