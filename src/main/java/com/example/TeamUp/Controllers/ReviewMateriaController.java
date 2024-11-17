package com.example.TeamUp.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.TeamUp.Entities.MateriaEntity;
import com.example.TeamUp.Entities.ReviewEntity;
import com.example.TeamUp.Services.ReviewMateriaService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/reviews")
public class ReviewMateriaController {

    @Autowired
    private ReviewMateriaService reviewMateriaService;

    /**
     * Asocia una materia existente a una review.
     *
     * @param reviewId  El ID de la review
     * @param materiaId El ID de la materia
     * @return La review actualizada con la materia asociada.
     */
    @PostMapping(value = "/{reviewId}/materias/{materiaId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ReviewEntity addMateriaToReview(@PathVariable("reviewId") Long reviewId, @PathVariable("materiaId") Long materiaId)
            throws EntityNotFoundException {
        return reviewMateriaService.addMateriaToReview(reviewId, materiaId);
    }

    /**
     * Obtiene la materia asociada a una review.
     *
     * @param reviewId El ID de la review
     * @return La materia asociada a la review.
     */
    @GetMapping(value = "/{reviewId}/materia")
    @ResponseStatus(code = HttpStatus.OK)
    public MateriaEntity getMateriaFromReview(@PathVariable("reviewId") Long reviewId) throws EntityNotFoundException {
        return reviewMateriaService.getMateriaFromReview(reviewId);
    }

    /**
     * Elimina la asociación entre una materia y una review.
     *
     * @param reviewId El ID de la review
     */
    @DeleteMapping(value = "/{reviewId}/materia")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeMateriaFromReview(@PathVariable("reviewId") Long reviewId) throws EntityNotFoundException {
        reviewMateriaService.removeMateriaFromReview(reviewId);
    }
}

