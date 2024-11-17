package com.example.TeamUp.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TeamUp.Entities.MateriaEntity;
import com.example.TeamUp.Entities.ReviewEntity;
import com.example.TeamUp.Repositories.MateriaRepository;
import com.example.TeamUp.Repositories.ReviewRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ReviewMateriaService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    /**
     * Asocia una materia existente a una review.
     *
     * @param reviewId   El ID de la review
     * @param materiaId  El ID de la materia
     * @return La review actualizada con la materia asociada.
     * @throws EntityNotFoundException Si la review o la materia no existen.
     */
    @Transactional
    public ReviewEntity addMateriaToReview(Long reviewId, Long materiaId) throws EntityNotFoundException {
        // Buscar la review
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("La review con ID " + reviewId + " no fue encontrada."));

        // Buscar la materia
        MateriaEntity materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new EntityNotFoundException("La materia con ID " + materiaId + " no fue encontrada."));

        // Asociar la materia a la review
        review.setMateria(materia);

        // Guardar los cambios en la base de datos
        return reviewRepository.save(review);
    }

    /**
     * Obtiene la materia asociada a una review.
     *
     * @param reviewId El ID de la review
     * @return La materia asociada.
     * @throws EntityNotFoundException Si la review no existe.
     */
    @Transactional
    public MateriaEntity getMateriaFromReview(Long reviewId) throws EntityNotFoundException {
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("La review con ID " + reviewId + " no fue encontrada."));
        return review.getMateria();
    }

    /**
     * Elimina la asociación entre una materia y una review.
     *
     * @param reviewId El ID de la review
     * @throws EntityNotFoundException Si la review no existe.
     */
    @Transactional
    public void removeMateriaFromReview(Long reviewId) throws EntityNotFoundException {
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("La review con ID " + reviewId + " no fue encontrada."));
        review.setMateria(null);
        reviewRepository.save(review);
    }
}
