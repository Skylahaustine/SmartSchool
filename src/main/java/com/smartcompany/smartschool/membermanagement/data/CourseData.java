package com.smartcompany.smartschool.membermanagement.data;

import com.smartcompany.smartschool.membermanagement.domain.Review;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseData {
    private Long id;
    private String name;
    private List<ReviewData> reviewData;
}
