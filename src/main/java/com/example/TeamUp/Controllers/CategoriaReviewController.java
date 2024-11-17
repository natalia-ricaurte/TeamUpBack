package com.example.TeamUp.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.TeamUp.Entities.ReviewEntity;
import com.example.TeamUp.Services.CategoriaReviewService;


import jakarta.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/categorias")
public class CategoriaReviewController {

    @Autowired
    private CategoriaReviewService categoriaReviewService;

    /**
     * Asocia una reseña existente a una categoría.
     *
     * @param categoriaId El ID de la categoría
     * @param reviewId    El ID de la reseña
     * @return La reseña asociada a la categoría.
     */
    @PostMapping(value = "/{categoriaId}/reviews/{reviewId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ReviewEntity addReviewToCategoria(@PathVariable("categoriaId") Long categoriaId, @PathVariable("reviewId") Long reviewId)
            throws EntityNotFoundException {
        return categoriaReviewService.addReviewToCategoria(categoriaId, reviewId);
    }

    /**
     * Obtiene la reseña asociada a una categoría.
     *
     * @param categoriaId El ID de la categoría
     * @return La reseña asociada a la categoría.
     */
    @GetMapping(value = "/{categoriaId}/review")
    @ResponseStatus(code = HttpStatus.OK)
    public ReviewEntity getReviewFromCategoria(@PathVariable("categoriaId") Long categoriaId) throws EntityNotFoundException {
        return categoriaReviewService.getReviewFromCategoria(categoriaId);
    }

    /**
     * Reemplaza la reseña asociada a una categoría.
     *
     * @param categoriaId El ID de la categoría
     * @param reviewId    El ID de la nueva reseña
     * @return La reseña actualizada en la categoría.
     */
    @PutMapping(value = "/{categoriaId}/reviews/{reviewId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ReviewEntity replaceReviewInCategoria(@PathVariable("categoriaId") Long categoriaId, @PathVariable("reviewId") Long reviewId)
            throws EntityNotFoundException {
        return categoriaReviewService.replaceReviewInCategoria(categoriaId, reviewId);
    }

    /**
     * Elimina la asociación de una reseña en una categoría.
     *
     * @param categoriaId El ID de la categoría
     */
    @DeleteMapping(value = "/{categoriaId}/review")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeReviewFromCategoria(@PathVariable("categoriaId") Long categoriaId) throws EntityNotFoundException {
        categoriaReviewService.removeReviewFromCategoria(categoriaId);
    }
}

