package com.example.TeamUp.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.TeamUp.Entities.UsuarioEntity;
import com.example.TeamUp.Entities.ReviewEntity;
import com.example.TeamUp.Repositories.UsuarioRepository;
import com.example.TeamUp.Repositories.ReviewRepository;
import com.example.TeamUp.exceptions.EntityNotFoundException;

@Service
public class ReviewUsuarioService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public UsuarioEntity addUsuarioEscritor(Long reviewId, Long usuarioId) throws EntityNotFoundException {
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("La reseña no fue encontrada"));

        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("El usuario no fue encontrado"));

        review.setUsuarioEscritor(usuario);
        reviewRepository.save(review);
        return usuario;
    }

    @Transactional
    public UsuarioEntity addUsuarioReceptor(Long reviewId, Long usuarioId) throws EntityNotFoundException {
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("La reseña no fue encontrada"));

        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("El usuario no fue encontrado"));

        review.setUsuarioRecibido(usuario);
        reviewRepository.save(review);
        return usuario;
    }

    @Transactional
    public List<UsuarioEntity> getUsuariosEscritores(Long reviewId) throws EntityNotFoundException {
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("La reseña no fue encontrada"));

        return List.of(review.getUsuarioEscritor());
    }

    @Transactional
    public List<UsuarioEntity> getUsuariosReceptores(Long reviewId) throws EntityNotFoundException {
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("La reseña no fue encontrada"));

        return List.of(review.getUsuarioRecibido());
    }

    @Transactional
    public List<UsuarioEntity> replaceUsuariosReceptores(Long reviewId, List<UsuarioEntity> usuarios) throws EntityNotFoundException {
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("La reseña no fue encontrada"));

        if (usuarios.size() != 1) {
            throw new IllegalArgumentException("Debe haber exactamente un usuario receptor");
        }

        review.setUsuarioRecibido(usuarios.get(0));
        reviewRepository.save(review);
        return List.of(review.getUsuarioRecibido());
    }

    @Transactional
    public void removeUsuarioEscritor(Long reviewId, Long usuarioId) throws EntityNotFoundException {
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("La reseña no fue encontrada"));

        if (review.getUsuarioEscritor() == null || !review.getUsuarioEscritor().getId().equals(usuarioId)) {
            throw new EntityNotFoundException("El usuario escritor no está asociado a esta reseña");
        }

        review.setUsuarioEscritor(null);
        reviewRepository.save(review);
    }

    @Transactional
    public void removeUsuarioReceptor(Long reviewId, Long usuarioId) throws EntityNotFoundException {
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("La reseña no fue encontrada"));

        if (review.getUsuarioRecibido() == null || !review.getUsuarioRecibido().getId().equals(usuarioId)) {
            throw new EntityNotFoundException("El usuario receptor no está asociado a esta reseña");
        }

        review.setUsuarioRecibido(null);
        reviewRepository.save(review);
    }
}