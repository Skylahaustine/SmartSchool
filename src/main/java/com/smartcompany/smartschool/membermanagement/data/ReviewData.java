package com.smartcompany.smartschool.membermanagement.data;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewData {
    private Long id;
    private String name;
    private Long courseId;

    private  CourseData courseData;
}
