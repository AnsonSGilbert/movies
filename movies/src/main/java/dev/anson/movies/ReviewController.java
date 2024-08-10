package dev.anson.movies;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Map<String, String> payload){
        try {
            String reviewBody = payload.get("reviewBody");
            String imdbId = payload.get("imdbId");

            if (reviewBody == null || imdbId == null) {
                return ResponseEntity.badRequest().build();
            }

            Review createdReview = reviewService.createReview(reviewBody, imdbId);

            return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            // Return internal server error status
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
