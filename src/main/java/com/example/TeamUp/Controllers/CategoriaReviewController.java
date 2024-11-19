package com.example.TeamUp.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.TeamUp.Entities.ReviewEntity;
import com.example.TeamUp.Services.CategoriaReviewService;
import com.example.TeamUp.dto.ReviewDTO;
import com.example.TeamUp.dto.ReviewDetailDTO;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/categorias")
public class CategoriaReviewController {

    @Autowired
    private CategoriaReviewService categoriaReviewService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Asocia una reseña existente a una categoría.
     *
     * @param categoriaId El ID de la categoría
     * @param reviewId    El ID de la reseña
     * @return La reseña asociada a la categoría.
     */
    @PostMapping(value = "/{categoriaId}/reviews/{reviewId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ReviewDetailDTO addReviewToCategoria(@PathVariable("categoriaId") Long categoriaId, @PathVariable("reviewId") Long reviewId)
            throws EntityNotFoundException {
        ReviewEntity reviewEntity = categoriaReviewService.addReviewToCategoria(categoriaId, reviewId);
        return modelMapper.map(reviewEntity, ReviewDetailDTO.class);
    }

    /**
     * Reemplaza la lista de reseñas asociadas a una categoría.
     *
     * @param categoriaId El ID de la categoría
     * @param reviews     Lista de reseñas a asociar a la categoría
     * @return Lista actualizada de reseñas asociadas a la categoría.
     */
    @PutMapping(value = "/{categoriaId}/reviews")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ReviewDetailDTO> replaceReviewsInCategoria(@PathVariable("categoriaId") Long categoriaId, @RequestBody ReviewDTO review)
            throws EntityNotFoundException {
        ReviewEntity entity = modelMapper.map(review, new TypeToken<ReviewEntity>() {}.getType());
        ReviewEntity reviewList = categoriaReviewService.replaceReviewInCategoria(categoriaId, entity.getId());
        return modelMapper.map(reviewList, new TypeToken<List<ReviewDetailDTO>>() {}.getType());
    }

    /**
     * Elimina la asociación entre una reseña y una categoría.
     *
     * @param categoriaId El ID de la categoría
     * @param reviewId    El ID de la reseña que se desasocia
     */
    @DeleteMapping(value = "/{categoriaId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeReviewFromCategoria(@PathVariable("categoriaId") Long categoriaId)
            throws EntityNotFoundException {
        categoriaReviewService.removeReviewFromCategoria(categoriaId);
    }
}