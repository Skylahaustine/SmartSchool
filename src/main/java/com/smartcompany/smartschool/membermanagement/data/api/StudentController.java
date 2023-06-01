package com.smartcompany.smartschool.membermanagement.data.api;

import com.smartcompany.smartschool.common.APIException;
import com.smartcompany.smartschool.common.Pager;
import com.smartcompany.smartschool.membermanagement.data.StudentData;
import com.smartcompany.smartschool.membermanagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class StudentController {
    private  final StudentService studentService;
    @PostMapping("/student")
public ResponseEntity<Pager<StudentData>> createStudent(@RequestBody StudentData studentData){
        System.out.println("studentData = " + studentData);
if(studentData.getId() != null){
throw APIException.badRequest("Cannot Create a Student That has an id" + studentData.getId());

}
StudentData result = studentService.save(studentData).entityToDto();
Pager<StudentData> pagers = new Pager<>();
pagers.setCode("0");
        pagers.setMessage("Student Saved Successfully");
        pagers.setContent(result);

return  ResponseEntity.status(HttpStatus.CREATED).body(pagers);
}
}
