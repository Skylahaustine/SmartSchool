package com.smartcompany.smartschool.membermanagement.service;

import com.smartcompany.smartschool.membermanagement.data.CourseData;
import com.smartcompany.smartschool.membermanagement.data.ReviewData;
import com.smartcompany.smartschool.membermanagement.domain.Course;
import com.smartcompany.smartschool.membermanagement.domain.Review;
import com.smartcompany.smartschool.membermanagement.domain.repository.CourseRepository;
import com.smartcompany.smartschool.membermanagement.domain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor


public class CourseService {
    private  final CourseRepository courseRepository;
    private  final ReviewRepository reviewRepository;

    public CourseData saveCourse(CourseData courseData) {
        Course course = new Course();
        course.setName(courseData.getName());

        Course  course1 = courseRepository.save(course);

        if (courseData.getReviewData() != null) {
            List<Review> reviews = courseData.getReviewData().stream()
                    .map(reviewData -> {
                        Review review = new Review();

                        review.setName(reviewData.getName());

review.setCourse(course);
                        return reviewRepository.save(review);
                    })
                    .collect(Collectors.toList());
            course.setReviews(reviews);
        }




        courseData.setId(course.getId());

        return course1.entityToDto();
    }


}
