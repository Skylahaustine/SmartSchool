package com.smartcompany.smartschool.membermanagement.service;

import com.smartcompany.smartschool.membermanagement.data.ReviewData;
import com.smartcompany.smartschool.membermanagement.domain.Course;
import com.smartcompany.smartschool.membermanagement.domain.Review;
import com.smartcompany.smartschool.membermanagement.domain.repository.CourseRepository;
import com.smartcompany.smartschool.membermanagement.domain.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final CourseRepository courseRepository;
    private final ReviewRepository reviewRepository;

    public ReviewService(CourseRepository courseRepository, ReviewRepository reviewRepository) {
        this.courseRepository = courseRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewData> saveReviews(Long bookId, ReviewData reviewData) {
        Course course = courseRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Course not Found with id " + bookId));


        Review review = new Review();
        review.setName(reviewData.getName());
        review.setCourse(course);

        Review savedReview = reviewRepository.save(review);
        return Collections.singletonList(savedReview.entyToDto());

    }


    public void deleteById(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review with id" + id));

        reviewRepository.deleteById(id);
    }

    public ReviewData updateById(Long id, ReviewData reviewData) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found with id " + id));


        review.setName(reviewData.getName());
        if (reviewData.getCourseId() != null) {
            Optional<Course> optionalCourse = courseRepository.findById(reviewData.getCourseId());
            if (optionalCourse.isEmpty()) {
                throw new RuntimeException("Course not found with id: " + reviewData.getCourseId());
            }
            Course course = optionalCourse.get();
            review.setCourse(course);
        }

        Review reviewToSave = reviewRepository.save(review);

        return reviewToSave.entyToDto();


    }
}
