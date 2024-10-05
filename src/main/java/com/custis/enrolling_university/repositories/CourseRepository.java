package com.custis.enrolling_university.repositories;

import com.custis.enrolling_university.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
