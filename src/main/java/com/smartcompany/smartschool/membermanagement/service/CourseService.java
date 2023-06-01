package com.smartcompany.smartschool.membermanagement.service;

import com.smartcompany.smartschool.common.APIException;
import com.smartcompany.smartschool.membermanagement.data.CourseData;
import com.smartcompany.smartschool.membermanagement.domain.Course;
import com.smartcompany.smartschool.membermanagement.domain.Review;
import com.smartcompany.smartschool.membermanagement.domain.repository.CourseRepository;
import com.smartcompany.smartschool.membermanagement.domain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor


public class CourseService {
    private final CourseRepository courseRepository;
    private final ReviewRepository reviewRepository;

    public CourseData saveCourse(CourseData courseData) {
        Course course = new Course();
        course.setName(courseData.getName());

        Course course1 = courseRepository.save(course);

        if (courseData.getReviews() != null) {
            List<Review> reviews = courseData.getReviews().stream()
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


    public Page<Course> fetchCourses(final Pageable pageable) {
        Page<Course> courses = courseRepository.findAll(pageable);
//        List<CourseData> courseData = courses.stream()
//                .map(Course::entityToDto)
//                .collect(Collectors.toList());
        return courses;


    }

    public CourseData fetchCourseById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            return course.get().entityToDto();
        } else

            throw new IllegalArgumentException("Course by id" + id + "not found");

    }

    public CourseData updateCourseByID(Long id, CourseData courseData) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            throw APIException.notFound("Cours with id {0} Not Found", id);
        }
        Course course1 = course.get();
        course1.setName(courseData.getName());

        List<Review> reviews = new ArrayList<>();
        if (courseData.getReviews() != null) {
            course1.getReviews().forEach(review ->
                    review.setCourse(null));
            course1.getReviews().clear();


            courseData.getReviews().forEach(reviewData -> {
                Review review = new Review();
                review.setName(review.getName());
                review.setCourse(course1);
                reviews.add(reviewRepository.save(review));
            });


        }
        course1.setReviews(reviews);
        Course courseToSave = courseRepository.save(course1);
        return courseToSave.entityToDto();


    }


    public void deleteCourseByID(Long id) {
        courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Course not found with id " + id));
        courseRepository.deleteById(id);
    }
}
