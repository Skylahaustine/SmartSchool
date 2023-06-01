package com.smartcompany.smartschool.membermanagement.domain;

import com.smartcompany.smartschool.membermanagement.data.CourseData;
import com.smartcompany.smartschool.membermanagement.data.ReviewData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class Course {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String courseSecret;
    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
    private List<Review> reviews;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;

    public CourseData entityToDto(){
        CourseData courseDto= new CourseData();
courseDto.setId(id);
courseDto.setName(name);

if (reviews != null){
    List<String> allReviews = new ArrayList<>();
    List<ReviewData> reviewData = reviews.stream()
            .map(Review::entyToDto)
            .collect(Collectors.toList());

courseDto.setReviews(reviewData);

}


return courseDto;

    }




}
