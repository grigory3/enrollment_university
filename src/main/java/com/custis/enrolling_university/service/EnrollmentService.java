package com.custis.enrolling_university.service;

import com.custis.enrolling_university.customExceptions.NoFreePlacesException;
import com.custis.enrolling_university.customExceptions.RecordingTimeExpiredException;
import com.custis.enrolling_university.customExceptions.StudentNotFoundException;
import com.custis.enrolling_university.customExceptions.CourseNotFoundException;
import com.custis.enrolling_university.models.Course;
import com.custis.enrolling_university.models.Enrollment;
import com.custis.enrolling_university.models.Student;
import com.custis.enrolling_university.repositories.CourseRepository;
import com.custis.enrolling_university.repositories.EnrollmentRepository;
import com.custis.enrolling_university.repositories.StudentRepository;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Enrollment> getAllInfo() {
        return enrollmentRepository.findAll();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void enrollStudent(Long studentId, Long courseId) {

        Course course = courseRepository.findById(courseId).orElseThrow(CourseNotFoundException::new);

        if (course.getOccupiedSeats() >= course.getTotalSeats()) throw new NoFreePlacesException();

        ZonedDateTime now = ZonedDateTime.now();

        if (now.isBefore(course.getStartDate()) || now.isAfter(course.getEndDate()))
            throw new RecordingTimeExpiredException();

        Student student = studentRepository.findById(studentId).orElseThrow(StudentNotFoundException::new);

        Enrollment enrollment = new Enrollment();

        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollmentRepository.save(enrollment);

        course.setOccupiedSeats(course.getOccupiedSeats() + 1);

        courseRepository.save(course);

    }
}