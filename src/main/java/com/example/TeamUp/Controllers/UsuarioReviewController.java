package com.example.TeamUp.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.TeamUp.Entities.ReviewEntity;
import com.example.TeamUp.Services.UsuarioReviewService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/usuarios")
public class UsuarioReviewController {

    @Autowired
    private UsuarioReviewService usuarioReviewService;

    /**
     * Asocia una reseña existente a un usuario como escritor.
     *
     * @param usuarioId El ID del usuario que escribe la reseña
     * @param reviewId  El ID de la reseña que se asocia
     * @return La reseña asociada.
     */
    @PostMapping(value = "/{usuarioId}/reviewsEscritas/{reviewId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ReviewEntity addReviewEscritaToUsuario(@PathVariable("usuarioId") Long usuarioId, @PathVariable("reviewId") Long reviewId)
            throws EntityNotFoundException {
        return usuarioReviewService.addReviewEscrita(usuarioId, reviewId);
    }

    /**
     * Asocia una reseña existente a un usuario como receptor.
     *
     * @param usuarioId El ID del usuario que recibe la reseña
     * @param reviewId  El ID de la reseña que se asocia
     * @return La reseña asociada.
     */
    @PostMapping(value = "/{usuarioId}/reviewsRecibidas/{reviewId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ReviewEntity addReviewRecibidaToUsuario(@PathVariable("usuarioId") Long usuarioId, @PathVariable("reviewId") Long reviewId)
            throws EntityNotFoundException {
        return usuarioReviewService.addReviewRecibidas(usuarioId, reviewId);
    }

    /**
     * Obtiene todas las reseñas escritas por un usuario.
     *
     * @param usuarioId El ID del usuario
     * @return Lista de reseñas escritas por el usuario.
     */
    @GetMapping(value = "/{usuarioId}/reviewsEscritas")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ReviewEntity> getReviewsEscritasFromUsuario(@PathVariable("usuarioId") Long usuarioId) throws EntityNotFoundException {
        return usuarioReviewService.getReviewsEscritas(usuarioId);
    }

    /**
     * Obtiene todas las reseñas recibidas por un usuario.
     *
     * @param usuarioId El ID del usuario
     * @return Lista de reseñas recibidas por el usuario.
     */
    @GetMapping(value = "/{usuarioId}/reviewsRecibidas")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ReviewEntity> getReviewsRecibidasFromUsuario(@PathVariable("usuarioId") Long usuarioId) throws EntityNotFoundException {
        return usuarioReviewService.getReviewsRecibidas(usuarioId);
    }



    /**
     * Reemplaza las reseñas recibidas por un usuario.
     *
     * @param usuarioId El ID del usuario
     * @param reviews   Lista de reseñas a asociar al usuario como receptor
     * @return Lista actualizada de reseñas recibidas por el usuario.
     */
    @PutMapping(value = "/{usuarioId}/reviewsRecibidas")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ReviewEntity> replaceReviewsRecibidasInUsuario(@PathVariable("usuarioId") Long usuarioId, @RequestBody List<ReviewEntity> reviews)
            throws EntityNotFoundException {
        return usuarioReviewService.replaceReviewsRecibidas(usuarioId, reviews);
    }

    /**
     * Elimina la asociación entre una reseña escrita y un usuario.
     *
     * @param usuarioId El ID del usuario
     * @param reviewId  El ID de la reseña que se desasocia
     */
    @DeleteMapping(value = "/{usuarioId}/reviewsEscritas/{reviewId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeReviewEscritaFromUsuario(@PathVariable("usuarioId") Long usuarioId, @PathVariable("reviewId") Long reviewId)
            throws EntityNotFoundException {
        usuarioReviewService.removeReviewEscrita(usuarioId, reviewId);
    }

    /**
     * Elimina la asociación entre una reseña recibida y un usuario.
     *
     * @param usuarioId El ID del usuario
     * @param reviewId  El ID de la reseña que se desasocia
     */
    @DeleteMapping(value = "/{usuarioId}/reviewsRecibidas/{reviewId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeReviewRecibidaFromUsuario(@PathVariable("usuarioId") Long usuarioId, @PathVariable("reviewId") Long reviewId)
            throws EntityNotFoundException {
        usuarioReviewService.removeReviewRecibida(usuarioId, reviewId);
    }
}

