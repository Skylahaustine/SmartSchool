package com.smartcompany.smartschool.membermanagement.domain.repository;

import com.smartcompany.smartschool.membermanagement.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
