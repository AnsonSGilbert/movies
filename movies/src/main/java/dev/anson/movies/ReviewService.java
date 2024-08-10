package dev.anson.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, MongoTemplate mongoTemplate) {
        this.reviewRepository = reviewRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Review createReview(String reviewBody, String imdbId) {
        // Create a new Review object with the provided reviewBody
        Review review = new Review(reviewBody);

        // Save the review to the review repository
        review = reviewRepository.insert(review);

        // Update the associated movie with the new review
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("imdbId").is(imdbId)),
                new Update().push("reviewIds", review.getId()),
                Movie.class
        );

        return review;
    }
}
