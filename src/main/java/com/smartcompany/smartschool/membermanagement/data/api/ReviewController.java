package com.smartcompany.smartschool.membermanagement.data.api;

import com.smartcompany.smartschool.membermanagement.data.ReviewData;
import com.smartcompany.smartschool.membermanagement.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @PostMapping
    public ResponseEntity<List<ReviewData>> createReviews(@RequestParam("courseId") Long courseId, @RequestBody ReviewData reviewData
    ) {

        return new ResponseEntity<List<ReviewData>>(reviewService.saveReviews(courseId, reviewData), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        reviewService.deleteById(id);
        return new ResponseEntity<String>("deleted", HttpStatus.OK);

    }
    @PutMapping
    public  ResponseEntity<ReviewData> updateReview(@RequestParam("id") Long id, @RequestBody ReviewData reviewData){

        return  new ResponseEntity<ReviewData>(reviewService.updateById(id, reviewData), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ReviewData>> getReviews(){
        return reviewService.fetchReviews();
    }

}
