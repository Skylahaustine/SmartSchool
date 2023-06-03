package com.smartcompany.smartschool.membermanagement.data.api;

import com.smartcompany.smartschool.common.APIException;
import com.smartcompany.smartschool.common.Pager;
import com.smartcompany.smartschool.common.PaginationUtil;
import com.smartcompany.smartschool.membermanagement.data.StudentData;
import com.smartcompany.smartschool.membermanagement.domain.Student;
import com.smartcompany.smartschool.membermanagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/student")
public class StudentController {
    private  final StudentService studentService;


    @GetMapping
public ResponseEntity<Pager<List<StudentData>>> getStudents(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer size
    ){
        Pageable pageable = PaginationUtil.createPage(page, size);
        Page<StudentData> results= studentService.getStudents(pageable).map(Student::entityToDto);
        Pager<List<StudentData>> pager=  PaginationUtil.createListPager(results, "Student entries");


        return ResponseEntity.ok(pager);

    }

    @PostMapping
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

@PutMapping
    public  ResponseEntity<Pager<StudentData>> updateStudent(@RequestBody StudentData studentData){
        if (studentData.getId() == null) {
            throw APIException.badRequest("Cannot update student without an id");
        }
            StudentData result = studentService.save(studentData).entityToDto();

            Pager<StudentData> pagers = new Pager<>();
            pagers.setCode("0");
            pagers.setMessage("Student Update Successfully");
            pagers.setContent(result);
            return  ResponseEntity.ok(pagers);


}

    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deleteStudent(@PathVariable Long id){
        studentService.deleteStudentByID(id);
        return  new ResponseEntity<String>(" Student deleted", HttpStatus.OK);
    }
}
