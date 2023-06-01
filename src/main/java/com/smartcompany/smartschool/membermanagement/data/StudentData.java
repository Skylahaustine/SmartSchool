package com.smartcompany.smartschool.membermanagement.data;

import com.smartcompany.smartschool.membermanagement.domain.Course;
import lombok.Data;

import java.util.List;

@Data
public class StudentData {
    private Long id;
    private String firstName;
    private String secondName;
    private String surName;
    private String email;
    private String parentName;
    private String parentContact;
    private String studentContact;


    private String gender;
    private String address;

    private List<Long> courseIds;




}
