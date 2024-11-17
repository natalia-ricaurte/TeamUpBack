package com.example.TeamUp.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TeamUp.Entities.CategoriaEntity;
import com.example.TeamUp.Entities.ReviewEntity;
import com.example.TeamUp.Repositories.CategoriaRepository;
import com.example.TeamUp.Repositories.ReviewRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoriaReviewService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * Asocia una reseña a una categoría existente.
     *
     * @param categoriaId Identificador de la categoría.
     * @param reviewId    Identificador de la reseña.
     * @return Instancia de ReviewEntity asociada a la categoría.
     * @throws EntityNotFoundException si la categoría o la reseña no existen.
     */
    @Transactional
    public ReviewEntity addReviewToCategoria(Long categoriaId, Long reviewId) throws EntityNotFoundException {
        log.info("Inicia proceso de asociar la reseña con id = {} a la categoría con id = {}", reviewId, categoriaId);
        CategoriaEntity categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new EntityNotFoundException("La categoría no fue encontrada"));
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("La reseña no fue encontrada"));

        categoria.setReview(review);
        categoriaRepository.save(categoria);

        log.info("Finaliza proceso de asociar la reseña con id = {} a la categoría con id = {}", reviewId, categoriaId);
        return review;
    }

    /**
     * Obtiene la reseña asociada a una categoría.
     *
     * @param categoriaId Identificador de la categoría.
     * @return Instancia de ReviewEntity asociada a la categoría.
     * @throws EntityNotFoundException si la categoría no existe o no tiene una reseña asociada.
     */
    @Transactional
    public ReviewEntity getReviewFromCategoria(Long categoriaId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar la reseña asociada a la categoría con id = {}", categoriaId);
        CategoriaEntity categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new EntityNotFoundException("La categoría no fue encontrada"));

        if (categoria.getReview() == null) {
            throw new EntityNotFoundException("La categoría no tiene una reseña asociada");
        }

        log.info("Finaliza proceso de consultar la reseña asociada a la categoría con id = {}", categoriaId);
        return categoria.getReview();
    }

    /**
     * Reemplaza la reseña asociada a una categoría.
     *
     * @param categoriaId Identificador de la categoría.
     * @param reviewId    Identificador de la nueva reseña.
     * @return Instancia de ReviewEntity actualizada en la categoría.
     * @throws EntityNotFoundException si la categoría o la reseña no existen.
     */
    @Transactional
    public ReviewEntity replaceReviewInCategoria(Long categoriaId, Long reviewId) throws EntityNotFoundException {
        log.info("Inicia proceso de reemplazar la reseña de la categoría con id = {}", categoriaId);
        CategoriaEntity categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new EntityNotFoundException("La categoría no fue encontrada"));
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("La reseña no fue encontrada"));

        categoria.setReview(review);
        categoriaRepository.save(categoria);

        log.info("Finaliza proceso de reemplazar la reseña de la categoría con id = {}", categoriaId);
        return review;
    }

    /**
     * Elimina la asociación de una reseña en una categoría.
     *
     * @param categoriaId Identificador de la categoría.
     * @throws EntityNotFoundException si la categoría no existe o no tiene una reseña asociada.
     */
    @Transactional
    public void removeReviewFromCategoria(Long categoriaId) throws EntityNotFoundException {
        log.info("Inicia proceso de eliminar la reseña asociada a la categoría con id = {}", categoriaId);
        CategoriaEntity categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new EntityNotFoundException("La categoría no fue encontrada"));

        if (categoria.getReview() == null) {
            throw new EntityNotFoundException("La categoría no tiene una reseña asociada para eliminar");
        }

        categoria.setReview(null);
        categoriaRepository.save(categoria);

        log.info("Finaliza proceso de eliminar la reseña asociada a la categoría con id = {}", categoriaId);
    }
}
