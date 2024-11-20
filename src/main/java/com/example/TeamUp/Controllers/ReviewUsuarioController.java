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

import com.example.TeamUp.Entities.UsuarioEntity;
import com.example.TeamUp.Entities.ReviewEntity;
import com.example.TeamUp.Services.ReviewUsuarioService;
import com.example.TeamUp.dto.UsuarioDTO;
import com.example.TeamUp.dto.UsuarioDetailDTO;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/reviews")
public class ReviewUsuarioController {

    @Autowired
    private ReviewUsuarioService reviewUsuarioService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Asocia un usuario existente a una reseña como escritor.
     *
     * @param reviewId  El ID de la reseña
     * @param usuarioId El ID del usuario que se asocia
     * @return El usuario asociado a la reseña.
          * @throws com.example.TeamUp.exceptions.EntityNotFoundException 
          */
         @PostMapping(value = "/{reviewId}/usuariosEscritores/{usuarioId}")
         @ResponseStatus(code = HttpStatus.OK)
         public UsuarioDetailDTO addUsuarioEscritorToReview(@PathVariable("reviewId") Long reviewId, @PathVariable("usuarioId") Long usuarioId)
                 throws EntityNotFoundException, com.example.TeamUp.exceptions.EntityNotFoundException {
        UsuarioEntity usuarioEntity = reviewUsuarioService.addUsuarioEscritor(reviewId, usuarioId);
        return modelMapper.map(usuarioEntity, UsuarioDetailDTO.class);
    }

    /**
     * Asocia un usuario existente a una reseña como receptor.
     *
     * @param reviewId  El ID de la reseña
     * @param usuarioId El ID del usuario que se asocia
     * @return El usuario asociado a la reseña.
          * @throws com.example.TeamUp.exceptions.EntityNotFoundException 
          */
         @PostMapping(value = "/{reviewId}/usuariosReceptores/{usuarioId}")
         @ResponseStatus(code = HttpStatus.OK)
         public UsuarioDetailDTO addUsuarioReceptorToReview(@PathVariable("reviewId") Long reviewId, @PathVariable("usuarioId") Long usuarioId)
                 throws EntityNotFoundException, com.example.TeamUp.exceptions.EntityNotFoundException {
        UsuarioEntity usuarioEntity = reviewUsuarioService.addUsuarioReceptor(reviewId, usuarioId);
        return modelMapper.map(usuarioEntity, UsuarioDetailDTO.class);
    }

    /**
     * Obtiene todos los usuarios escritores de una reseña.
     *
     * @param reviewId El ID de la reseña
     * @return Lista de usuarios escritores de la reseña.
          * @throws com.example.TeamUp.exceptions.EntityNotFoundException 
          */
         @GetMapping(value = "/{reviewId}/usuariosEscritores")
         @ResponseStatus(code = HttpStatus.OK)
         public List<UsuarioDetailDTO> getUsuariosEscritoresFromReview(@PathVariable("reviewId") Long reviewId) throws EntityNotFoundException, com.example.TeamUp.exceptions.EntityNotFoundException {
        List<UsuarioEntity> usuarioEntities = reviewUsuarioService.getUsuariosEscritores(reviewId);
        return modelMapper.map(usuarioEntities, new TypeToken<List<UsuarioDetailDTO>>() {}.getType());
    }

    /**
     * Obtiene todos los usuarios receptores de una reseña.
     *
     * @param reviewId El ID de la reseña
     * @return Lista de usuarios receptores de la reseña.
          * @throws com.example.TeamUp.exceptions.EntityNotFoundException 
          */
         @GetMapping(value = "/{reviewId}/usuariosReceptores")
         @ResponseStatus(code = HttpStatus.OK)
         public List<UsuarioDetailDTO> getUsuariosReceptoresFromReview(@PathVariable("reviewId") Long reviewId) throws EntityNotFoundException, com.example.TeamUp.exceptions.EntityNotFoundException {
        List<UsuarioEntity> usuarioEntities = reviewUsuarioService.getUsuariosReceptores(reviewId);
        return modelMapper.map(usuarioEntities, new TypeToken<List<UsuarioDetailDTO>>() {}.getType());
    }

    /**
     * Reemplaza los usuarios receptores de una reseña.
     *
     * @param reviewId El ID de la reseña
     * @param usuarios Lista de usuarios a asociar a la reseña como receptores
     * @return Lista actualizada de usuarios receptores de la reseña.
          * @throws com.example.TeamUp.exceptions.EntityNotFoundException 
          */
         @PutMapping(value = "/{reviewId}/usuariosReceptores")
         @ResponseStatus(code = HttpStatus.OK)
         public List<UsuarioDetailDTO> replaceUsuariosReceptoresInReview(@PathVariable("reviewId") Long reviewId, @RequestBody List<UsuarioDTO> usuarios)
                 throws EntityNotFoundException, com.example.TeamUp.exceptions.EntityNotFoundException {
        List<UsuarioEntity> usuarioEntities = modelMapper.map(usuarios, new TypeToken<List<UsuarioEntity>>() {}.getType());
        List<UsuarioEntity> updatedUsuarios = reviewUsuarioService.replaceUsuariosReceptores(reviewId, usuarioEntities);
        return modelMapper.map(updatedUsuarios, new TypeToken<List<UsuarioDetailDTO>>() {}.getType());
    }

    /**
     * Elimina la asociación entre un usuario escritor y una reseña.
     *
     * @param reviewId  El ID de la reseña
     * @param usuarioId El ID del usuario que se desasocia
          * @throws com.example.TeamUp.exceptions.EntityNotFoundException 
          */
         @DeleteMapping(value = "/{reviewId}/usuariosEscritores/{usuarioId}")
         @ResponseStatus(code = HttpStatus.NO_CONTENT)
         public void removeUsuarioEscritorFromReview(@PathVariable("reviewId") Long reviewId, @PathVariable("usuarioId") Long usuarioId)
                 throws EntityNotFoundException, com.example.TeamUp.exceptions.EntityNotFoundException {
        reviewUsuarioService.removeUsuarioEscritor(reviewId, usuarioId);
    }

    /**
     * Elimina la asociación entre un usuario receptor y una reseña.
     *
     * @param reviewId  El ID de la reseña
     * @param usuarioId El ID del usuario que se desasocia
          * @throws com.example.TeamUp.exceptions.EntityNotFoundException 
          */
         @DeleteMapping(value = "/{reviewId}/usuariosReceptores/{usuarioId}")
         @ResponseStatus(code = HttpStatus.NO_CONTENT)
         public void removeUsuarioReceptorFromReview(@PathVariable("reviewId") Long reviewId, @PathVariable("usuarioId") Long usuarioId)
                 throws EntityNotFoundException, com.example.TeamUp.exceptions.EntityNotFoundException {
        reviewUsuarioService.removeUsuarioReceptor(reviewId, usuarioId);
    }
}
