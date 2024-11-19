package com.example.TeamUp.Controllers;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.example.TeamUp.Entities.ReviewEntity;
import com.example.TeamUp.Services.ReviewService;
import com.example.TeamUp.dto.ReviewDTO;
import com.example.TeamUp.dto.ReviewDetailDTO;
import com.example.TeamUp.exceptions.EntityNotFoundException;
import com.example.TeamUp.exceptions.IllegalOperationException;


@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private final ReviewService reviewService;

    @Autowired
    private ModelMapper modelMapper;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    //get todos los reviews
    @GetMapping
    public List<ReviewDetailDTO> getReviews() throws  IllegalOperationException {
		List<ReviewEntity> review = reviewService.getReviews();
		return modelMapper.map(review, new TypeToken<List<ReviewDetailDTO>>() {
        }.getType());
	}
       

    //get review por id
    @GetMapping("/{id}")
    public ReviewDetailDTO getReview(@PathVariable Long id) throws EntityNotFoundException{
        Optional<ReviewEntity> review = reviewService.getReviewById(id);
		return modelMapper.map(review, new TypeToken<ReviewDetailDTO>() {
        }.getType());
        
    }

    //crear un review
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ReviewDTO create(@RequestBody ReviewDTO reviewDTO) throws IllegalOperationException, EntityNotFoundException {
        ReviewEntity reviewEntity = reviewService.saveReview(modelMapper.map(reviewDTO, ReviewEntity.class));
        return modelMapper.map(reviewEntity, ReviewDTO.class);
    } 

    //eliminar un review

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        
        reviewService.deleteReview(id);
}
    

}
