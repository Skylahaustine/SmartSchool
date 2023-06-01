package com.smartcompany.smartschool.membermanagement.service;

import com.smartcompany.smartschool.membermanagement.data.StudentData;
import com.smartcompany.smartschool.membermanagement.domain.Course;
import com.smartcompany.smartschool.membermanagement.domain.Student;
import com.smartcompany.smartschool.membermanagement.domain.repository.CourseRepository;
import com.smartcompany.smartschool.membermanagement.domain.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class StudentService {

private  final StudentRepository studentRepository;
private  final CourseRepository courseRepository;

public Student save(StudentData studentData){
if(studentData.getId() != null){
return  update(studentData);
}else{
return  create(studentData);
}
}

private Student create(StudentData studentData){
    Student student= new Student();

    student.setEmail(studentData.getEmail());
    student.setFirstName(studentData.getFirstName());
    student.setSecondName(studentData.getSecondName());
    student.setSurName(studentData.getSurName());
    student.setGender(studentData.getGender());
    student.setAddress(studentData.getAddress());
    student.setParentName(studentData.getParentName());
    student.setStudentContact(studentData.getStudentContact());
    student.setParentContact(studentData.getStudentContact());

    List<Course> courses = new ArrayList<>();
    List<Long> courseIds = studentData.getCourseIds();
    for (Long courseId : courseIds) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            courses.add(course);
        }
    }

    student.setCourses(courses);


    return  studentRepository.save(student);



}

private  Student update(StudentData studentData){

    return  null;
}
}
