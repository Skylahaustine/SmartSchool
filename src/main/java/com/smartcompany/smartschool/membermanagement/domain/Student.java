package com.smartcompany.smartschool.membermanagement.domain;

import com.smartcompany.smartschool.membermanagement.data.CourseData;
import com.smartcompany.smartschool.membermanagement.data.StudentData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String gender;
    private String address;
    @Column(unique = true, nullable = false)
    private String email;
    private String parentName;
    private String parentContact;
    private String studentContact;


    private String secondName;
    private String surName;
    private String studentSecret;
    @ManyToMany
    @JoinTable(name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")

    )
    private List<Course> courses;


    public StudentData entityToDto (){
        StudentData studentData= new StudentData();
        studentData.setEmail(email);
        studentData.setFirstName(firstName);
if(courses !=null){
List<CourseData> courseData= courses.stream()
        .map(Course::entityToDto)
        .toList();

studentData.setCourses(courseData);
}


        return  studentData;


    }
}

