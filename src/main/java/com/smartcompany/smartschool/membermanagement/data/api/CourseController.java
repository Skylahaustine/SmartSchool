package com.smartcompany.smartschool.membermanagement.data.api;


import com.smartcompany.smartschool.common.Pager;
import com.smartcompany.smartschool.common.PaginationUtil;
import com.smartcompany.smartschool.membermanagement.data.CourseData;
import com.smartcompany.smartschool.membermanagement.domain.Course;
import com.smartcompany.smartschool.membermanagement.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/course")
    public ResponseEntity<CourseData> createCourse(@RequestBody CourseData courseData) {
        return new ResponseEntity<CourseData>(courseService.saveCourse(courseData), HttpStatus.CREATED);
    }

    @GetMapping("/course")
    public ResponseEntity<Pager<List<CourseData>>> getCourses(
            @RequestParam(value = "page", required = false) final Integer pageNo,
            @RequestParam(value = "pageSize", required = false) final Integer pageSize) {
        Pageable pageable = PaginationUtil.createUnPaged(pageNo, pageSize);
        Page<CourseData> page=  courseService.fetchCourses(pageable).map(Course::entityToDto);
        @SuppressWarnings("unchecked")  Pager<List<CourseData>> pagers = (Pager<List<CourseData>>) PaginationUtil.toPager(page, "Course Data");

        return ResponseEntity.status(HttpStatus.OK).body(pagers);
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<CourseData> getCourseById(@PathVariable Long id) {
        CourseData courseData = courseService.fetchCourseById(id);
        if (courseData != null) {
            return new ResponseEntity<>(courseData, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/course/{id}")
    public ResponseEntity<CourseData> updateCourse(@PathVariable Long id, @RequestBody CourseData courseData) {

        return new ResponseEntity<CourseData>(courseService.updateCourseByID(id, courseData), HttpStatus.OK);
    }

    @DeleteMapping("/course/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {

        courseService.deleteCourseByID(id);
        return new ResponseEntity<String>("deleted", HttpStatus.OK);
    }

}
