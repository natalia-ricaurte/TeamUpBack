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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.TeamUp.Entities.CategoriaEntity;
import com.example.TeamUp.Entities.ReviewEntity;
import com.example.TeamUp.Services.CategoriaReviewService;
import com.example.TeamUp.Services.ReviewCategoriaService;
import com.example.TeamUp.dto.CategoriaDTO;
import com.example.TeamUp.dto.CategoriaDetailDTO;

import jakarta.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/reviews")
public class ReviewCategoriaController {

    @Autowired
    private ReviewCategoriaService reviewCategoriaService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Asocia una categoría existente a una reseña.
     *
     * @param reviewId    El ID de la reseña
     * @param categoriaId El ID de la categoría
     * @return La categoría asociada a la reseña.
     */
    @PostMapping(value = "/{reviewId}/categorias/{categoriaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public CategoriaDetailDTO addCategoriaToReview(@PathVariable("reviewId") Long reviewId, @PathVariable("categoriaId") Long categoriaId)
			throws EntityNotFoundException {
		CategoriaEntity categoriaEntity = reviewCategoriaService.addCategoriaToReview (reviewId, categoriaId);
		return modelMapper.map(categoriaEntity, CategoriaDetailDTO.class);
	}

    /**
     * Obtiene todas las categorías asociadas a una reseña.
     *
     * @param reviewId El ID de la reseña
     * @return Lista de categorías asociadas a la reseña.
     */

    @GetMapping(value = "/{reviewId}/categorias")
	@ResponseStatus(code = HttpStatus.OK)
	public List<CategoriaDetailDTO> getCategoriasFromReview(@PathVariable("reviewId") Long reviewId) throws EntityNotFoundException {
		List<CategoriaEntity> categoriaEntity = reviewCategoriaService.getCategoriasByReview(reviewId);

		return modelMapper.map(categoriaEntity, new TypeToken<List<CategoriaDetailDTO>>() {
		}.getType());

	}

    /**
     * Reemplaza la lista de categorías asociadas a una reseña.
     *
     * @param reviewId   El ID de la reseña
     * @param categorias Lista de categorías a asociar a la reseña
     * @return Lista actualizada de categorías asociadas a la reseña.
     */
    @PutMapping(value = "/{reviewId}/categorias")
    @ResponseStatus(code = HttpStatus.OK)
	public List<CategoriaDetailDTO> replaceCategoriasInReview(@PathVariable("reviewId") Long reviewId, @RequestBody List<CategoriaDTO> categorias)
			throws EntityNotFoundException {
		List<CategoriaEntity> entities = modelMapper.map(categorias, new TypeToken<List<CategoriaEntity>>() {
		}.getType());
        List<CategoriaEntity> categoriaList = reviewCategoriaService.replaceCategoriasInReview(reviewId, entities);
		return modelMapper.map(categoriaList, new TypeToken<List<CategoriaDetailDTO>>() {
		}.getType());

	}

    /**
     * Elimina la asociación entre una categoría y una reseña.
     *
     * @param reviewId    El ID de la reseña
     * @param categoriaId El ID de la categoría que se desasocia
     */
    @DeleteMapping(value = "/{reviewId}/categorias/{categoriaId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeArticulo(@PathVariable("reviewId") Long reviewId, @PathVariable("categoriaId") Long categoriaId)
			throws EntityNotFoundException {
             reviewCategoriaService.removeCategoriaFromReview(reviewId, categoriaId);;
	}
}

