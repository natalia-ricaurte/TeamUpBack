package com.example.TeamUp.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TeamUp.Entities.ReviewEntity;
import com.example.TeamUp.Services.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    //get todos los reviews
    @GetMapping
    public List<ReviewEntity> getReviews() {
        return reviewService.getReviews();
    }

    //get review por id
    @GetMapping("/{id}")
    public Optional<ReviewEntity> getReview(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

    //crear un review
    @PostMapping
    public ReviewEntity addReview(ReviewEntity review) {
        return reviewService.saveReview(review);
    }

    //eliminar un review
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
    

}
