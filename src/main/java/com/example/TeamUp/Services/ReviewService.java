package com.example.TeamUp.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TeamUp.Entities.ReviewEntity;
import com.example.TeamUp.Repositories.ReviewRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;

    
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    //get todas las reviews
    public List<ReviewEntity> getReviews() {
        return reviewRepository.findAll();
    }

    //get review por id
    public Optional<ReviewEntity> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    //Crear o actualziar review
    public ReviewEntity saveReview(ReviewEntity review) {
        return reviewRepository.save(review);
    }

    //Borrar review
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
    
    
}
