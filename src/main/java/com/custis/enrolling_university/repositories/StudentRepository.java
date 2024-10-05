package com.custis.enrolling_university.repositories;

import com.custis.enrolling_university.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
