package com.smartcompany.smartschool.membermanagement.data.api;

import com.smartcompany.smartschool.membermanagement.data.ReviewData;
import com.smartcompany.smartschool.membermanagement.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @PostMapping("/reviews")
    public ResponseEntity<List<ReviewData>> createReviews(@RequestParam("bookId") Long bookId, @RequestBody ReviewData reviewData
    ) {

        return new ResponseEntity<List<ReviewData>>(reviewService.saveReviews(bookId, reviewData), HttpStatus.CREATED);
    }

    @DeleteMapping("review/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        reviewService.deleteById(id);
        return new ResponseEntity<String>("deleted", HttpStatus.OK);

    }
    @PutMapping("review/{id}")
    public  ResponseEntity<ReviewData> updateReview(@PathVariable Long id, @RequestBody ReviewData reviewData){
        return  new ResponseEntity<ReviewData>(reviewService.updateById(id, reviewData), HttpStatus.OK);
    }

}
