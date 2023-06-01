package com.smartcompany.smartschool.membermanagement.domain;

import com.smartcompany.smartschool.membermanagement.data.CourseData;
import com.smartcompany.smartschool.membermanagement.data.ReviewData;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String reviewSecret;

    @ManyToOne
    @JoinColumn( name = "my_course_id", nullable = false)
    private Course course;

    public ReviewData entyToDto(){
        ReviewData reviewDto= new ReviewData();
                reviewDto.setId(id);
        reviewDto.setName(name);
        if(course !=null){
            System.out.println("course.getName() = " + course.getName());
            CourseData courseData = new CourseData();
            reviewDto.setCourseId(course.getId());
            reviewDto.setCourseName(course.getName());


        }


        return reviewDto;
    }

}
