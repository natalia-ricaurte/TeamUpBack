package com.example.TeamUp.Services;

import java.util.List;
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
public class ReviewCategoriaService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public ReviewEntity addCategoriasToReview(Long reviewId, List<Long> categoriaIds) throws EntityNotFoundException {
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("La reseña no fue encontrada"));

        List<CategoriaEntity> categorias = categoriaRepository.findAllById(categoriaIds);
        if (categorias.size() != categoriaIds.size()) {
            throw new EntityNotFoundException("Una o más categorías no fueron encontradas");
        }

        review.getCategorias().addAll(categorias);
        return reviewRepository.save(review);
    }

    @Transactional
    public CategoriaEntity addCategoriaToReview(Long reviewId, Long categoriaId) throws EntityNotFoundException {
        log.info("Inicia proceso de asociar una categoría con id = {} a la reseña con id = {}", categoriaId, reviewId);
        Optional<ReviewEntity> reviewEntity = reviewRepository.findById(reviewId);
        Optional<CategoriaEntity> categoriaEntity = categoriaRepository.findById(categoriaId);

        if (reviewEntity.isEmpty()) {
            throw new EntityNotFoundException("La reseña no fue encontrada");
        }

        if (categoriaEntity.isEmpty()) {
            throw new EntityNotFoundException("La categoría no fue encontrada");
        }

        categoriaEntity.get().setReview(reviewEntity.get());
        reviewEntity.get().getCategorias().add(categoriaEntity.get());
        log.info("Finaliza proceso de asociar una categoría con id = {} a la reseña con id = {}", categoriaId, reviewId);
        return categoriaRepository.save(categoriaEntity.get());
    }

    @Transactional
    public List<CategoriaEntity> getCategoriasByReview(Long reviewId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar categorías asociadas a la reseña con id = {}", reviewId);
        Optional<ReviewEntity> reviewEntity = reviewRepository.findById(reviewId);

        if (reviewEntity.isEmpty()) {
            throw new EntityNotFoundException("La reseña no fue encontrada");
        }

        log.info("Finaliza proceso de consultar categorías asociadas a la reseña con id = {}", reviewId);
        return reviewEntity.get().getCategorias();
    }

    @Transactional
    public CategoriaEntity getCategoriaFromReview(Long reviewId, Long categoriaId)
            throws EntityNotFoundException {
        log.info("Inicia proceso de consultar la categoría con id = {} de la reseña con id = {}", categoriaId, reviewId);
        Optional<ReviewEntity> reviewEntity = reviewRepository.findById(reviewId);
        Optional<CategoriaEntity> categoriaEntity = categoriaRepository.findById(categoriaId);

        if (reviewEntity.isEmpty()) {
            throw new EntityNotFoundException("La reseña no fue encontrada");
        }

        if (categoriaEntity.isEmpty() || !categoriaEntity.get().getReview().equals(reviewEntity.get())) {
            throw new EntityNotFoundException("La categoría no está asociada a la reseña");
        }

        log.info("Finaliza proceso de consultar la categoría con id = {} de la reseña con id = {}", categoriaId, reviewId);
        return categoriaEntity.get();
    }

    @Transactional
    public List<CategoriaEntity> replaceCategoriasInReview(Long reviewId, List<CategoriaEntity> categorias)
            throws EntityNotFoundException {
        log.info("Inicia proceso de reemplazar las categorías de la reseña con id = {}", reviewId);
        Optional<ReviewEntity> reviewEntity = reviewRepository.findById(reviewId);

        if (reviewEntity.isEmpty()) {
            throw new EntityNotFoundException("La reseña no fue encontrada");
        }

        for (CategoriaEntity categoria : categorias) {
            Optional<CategoriaEntity> categoriaEntity = categoriaRepository.findById(categoria.getId());
            if (categoriaEntity.isEmpty()) {
                throw new EntityNotFoundException("Una de las categorías no fue encontrada");
            }
        }

        reviewEntity.get().getCategorias().clear();
        reviewEntity.get().getCategorias().addAll(categorias);

        for (CategoriaEntity categoria : categorias) {
            categoria.setReview(reviewEntity.get());
            categoriaRepository.save(categoria);
        }

        log.info("Finaliza proceso de reemplazar las categorías de la reseña con id = {}", reviewId);
        return reviewEntity.get().getCategorias();
    }

    @Transactional
    public void removeCategoriaFromReview(Long reviewId, Long categoriaId) throws EntityNotFoundException {
        log.info("Inicia proceso de eliminar la categoría con id = {} de la reseña con id = {}", categoriaId, reviewId);
        Optional<ReviewEntity> reviewEntity = reviewRepository.findById(reviewId);
        Optional<CategoriaEntity> categoriaEntity = categoriaRepository.findById(categoriaId);

        if (reviewEntity.isEmpty()) {
            throw new EntityNotFoundException("La reseña no fue encontrada");
        }

        if (categoriaEntity.isEmpty() || !categoriaEntity.get().getReview().equals(reviewEntity.get())) {
            throw new EntityNotFoundException("La categoría no está asociada a la reseña");
        }

        categoriaEntity.get().setReview(null);
        reviewEntity.get().getCategorias().remove(categoriaEntity.get());
        categoriaRepository.save(categoriaEntity.get());

        log.info("Finaliza proceso de eliminar la categoría con id = {} de la reseña con id = {}", categoriaId, reviewId);
    }
}

