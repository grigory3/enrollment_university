package com.custis.enrolling_university.repositories;

import com.custis.enrolling_university.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
