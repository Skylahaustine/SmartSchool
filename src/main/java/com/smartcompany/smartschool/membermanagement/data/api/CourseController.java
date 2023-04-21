package com.smartcompany.smartschool.membermanagement.data.api;


import com.smartcompany.smartschool.membermanagement.data.CourseData;
import com.smartcompany.smartschool.membermanagement.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/course")
public ResponseEntity<CourseData> createCourse(@RequestBody CourseData courseData){
return  new ResponseEntity<CourseData>(courseService.saveCourse(courseData), HttpStatus.CREATED);
    }

}
