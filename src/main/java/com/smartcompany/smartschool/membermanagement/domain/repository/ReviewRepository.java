package com.smartcompany.smartschool.membermanagement.domain.repository;

import com.smartcompany.smartschool.membermanagement.domain.Course;
import com.smartcompany.smartschool.membermanagement.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
