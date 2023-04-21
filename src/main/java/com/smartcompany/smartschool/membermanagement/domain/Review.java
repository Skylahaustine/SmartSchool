package com.smartcompany.smartschool.membermanagement.domain;

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
    private Course course;

    public ReviewData entyToDto(){
        ReviewData reviewDto= new ReviewData();
                reviewDto.setId(id);
        reviewDto.setName(name);

        return reviewDto;
    }

}
