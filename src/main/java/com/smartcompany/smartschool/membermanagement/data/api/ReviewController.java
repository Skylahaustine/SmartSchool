package com.smartcompany.smartschool.membermanagement.data.api;

import com.smartcompany.smartschool.common.Pager;
import com.smartcompany.smartschool.common.PaginationUtil;
import com.smartcompany.smartschool.membermanagement.data.ReviewData;
import com.smartcompany.smartschool.membermanagement.domain.Review;
import com.smartcompany.smartschool.membermanagement.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Pager<List<ReviewData>>> getReviews(
            @RequestParam(value = "page", required = false) final Integer pageNo,
            @RequestParam(value = "pageSize", required = false) final Integer pageSize){
        Pageable pageable = PaginationUtil.createUnPaged(pageNo, pageSize);
        Page<ReviewData> page = reviewService.fetchReviews(pageable).map(Review::entyToDto);

        @SuppressWarnings("unchecked") Pager<List<ReviewData>> pagers = (Pager<List<ReviewData>>) PaginationUtil.toPager(page, "Review Data");

        return ResponseEntity.status(HttpStatus.OK).body(pagers);
    }

}
