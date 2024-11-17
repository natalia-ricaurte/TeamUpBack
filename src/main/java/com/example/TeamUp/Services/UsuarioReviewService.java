package com.example.TeamUp.Services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TeamUp.Entities.ReviewEntity;
import com.example.TeamUp.Entities.UsuarioEntity;
import com.example.TeamUp.Repositories.ReviewRepository;
import com.example.TeamUp.Repositories.UsuarioRepository;
import com.example.TeamUp.exceptions.IllegalOperationException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioReviewService {

    @Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	/**
	 * Asocia una Sede existente a un Articulo
	 *
	 * @param usuarioId Identificador de la instancia de Articulo
	 * @param reviewId     Identificador de la instancia de Sede
	 * @return Instancia de ReviewEntity que fue asociada a Articulo
	 */
	@Transactional
	public ReviewEntity addReviewRecibidas(Long usuarioId, Long reviewId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle una reseña recibida al usuario con id = {}", usuarioId);
		Optional<ReviewEntity> reviewEntity = reviewRepository.findById(reviewId);
		Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);
	
		if (usuarioEntity.isEmpty())
			throw new EntityNotFoundException("El usuario no fue encontrado");
	
		if (reviewEntity.isEmpty())
			throw new EntityNotFoundException("La reseña no fue encontrada");
	
		// Establecer el usuario como receptor de la reseña
		reviewEntity.get().setUsuarioRecibido(usuarioEntity.get());
	
		// Añadir la reseña a la lista de reviews recibidas
		usuarioEntity.get().getReviewsRecibidas().add(reviewEntity.get());
	
		// Guardar los cambios
		reviewRepository.save(reviewEntity.get());
		usuarioRepository.save(usuarioEntity.get());
	
		log.info("Termina proceso de asociarle una reseña recibida al usuario con id = {}", usuarioId);
		return reviewEntity.get();
	}
	

	@Transactional
	public ReviewEntity addReviewEscrita(Long usuarioId, Long reviewId) throws EntityNotFoundException {
    log.info("Inicia proceso de asociarle una reseña escrita al usuario con id = {}", usuarioId);
    Optional<ReviewEntity> reviewEntity = reviewRepository.findById(reviewId);
    Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);

    if (usuarioEntity.isEmpty())
        throw new EntityNotFoundException("El usuario no fue encontrado");

    if (reviewEntity.isEmpty())
        throw new EntityNotFoundException("La reseña no fue encontrada");

    // Establecer el usuario como escritor de la reseña
    reviewEntity.get().setUsuarioEscritor(usuarioEntity.get());
    
    // Añadir la reseña a la lista de reviews escritas
    usuarioEntity.get().getReviewsEscritas().add(reviewEntity.get());

    // Guardar los cambios
    reviewRepository.save(reviewEntity.get());
    usuarioRepository.save(usuarioEntity.get());

    log.info("Termina proceso de asociarle una reseña escrita al usuario con id = {}", usuarioId);
    return reviewEntity.get();
}

	@Transactional
	public List<ReviewEntity> addReviews(Long usuarioId, List<ReviewEntity> reviews) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar las reviews asociados al usuario con id = {0}", usuarioId);
		Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);
		
        if  (usuarioEntity.isEmpty())
			throw new EntityNotFoundException("El artículo no fue encontrado");

		for (ReviewEntity review : reviews) {
			Optional<ReviewEntity> reviewEntity = reviewRepository.findById(review.getId());
			if  (reviewEntity.isEmpty())
				throw new EntityNotFoundException("La review no fue encontrada");
		}
		log.info("Finaliza proceso de reemplazar las reviews asociados al usuario con id = {0}", usuarioId);
	 usuarioEntity.get().setReviewsRecibidas(reviews);
		return usuarioEntity.get().getReviewsRecibidas();
	}

	/**
	 * Obtiene una colección de instancias de ReviewEntity asociadas a una instancia
	 * de UsuarioEntity
	 *
	 * @param usuarioId Identificador de la instancia de Articulo
	 * @return Colección de instancias de ReviewEntity asociadas a la instancia de
	 *         Articulo
	 */
	@Transactional
	public List<ReviewEntity> getReviewsRecibidas(Long usuarioId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todas las reviews del usuario con id = {0}", usuarioId);
		Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);
		if  (usuarioEntity.isEmpty())
			throw new EntityNotFoundException("El usuario no fue encontrado");
		log.info("Finaliza proceso de consultar todas las reviews del usuario con id = {0}", usuarioId);
		return usuarioEntity.get().getReviewsRecibidas();
	}

	@Transactional
	public List<ReviewEntity> getReviewsEscritas(Long usuarioId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todas las reviews del usuario con id = {0}", usuarioId);
		Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);
		if  (usuarioEntity.isEmpty())
			throw new EntityNotFoundException("El usuario no fue encontrado");
		log.info("Finaliza proceso de consultar todas las reviews del usuario con id = {0}", usuarioId);
		return usuarioEntity.get().getReviewsEscritas();
	}

	/**
	 * Obtiene una instancia de AuthorEntity asociada a una instancia de Book
	 *
	 * @param usuarioId Identificador de la instancia de Articulo
	 * @param reviewId     Identificador de la instancia de Sede
	 * @return La entidad de la Sede asociada al Articulo
	 */
	@Transactional
	public ReviewEntity getReviewRecibidas(Long usuarioId, Long reviewId)
			throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar una review del usuario con id = {0}", usuarioId);
		Optional<ReviewEntity> reviewEntity = reviewRepository.findById(reviewId);
		Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);

		if  (usuarioEntity.isEmpty())
			throw new EntityNotFoundException("El usuario no fue encontrado");

		if  (reviewEntity.isEmpty())
			throw new EntityNotFoundException("La review no fue encontrada");

		log.info("Termina proceso de consultar una review del usuario con id = {0}", usuarioId);
		if ( usuarioEntity.get().getReviewsRecibidas().contains (reviewEntity.get()))
			throw new IllegalOperationException("La review no esta asociada al artículo");

		return reviewEntity.get();
	}

	@Transactional
	/**
	 * Remplaza las instancias de review asociadas a una instancia de Articulo
	 *
	 * @param usuarioId Identificador de la instancia de Articulo
	 * @param list       Colección de instancias de ReviewEntity a asociar a instancia
	 *                   de Articulo
	 * @return Nueva colección de ReviewEntity asociada a la instancia de Articulo
	 */
	public List<ReviewEntity> replaceReviewsRecibidas(Long usuarioId, List<ReviewEntity> list) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar las reviews del usuario con id = {0}", usuarioId);
		Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);
		if  (usuarioEntity.isEmpty())
			throw new EntityNotFoundException("El usuario no fue encontrado");

		for (ReviewEntity review : list) {
			Optional<ReviewEntity> reviewEntity = reviewRepository.findById(review.getId());
			if  (reviewEntity.isEmpty())
				throw new EntityNotFoundException("La review no fue encontrada");

			if ( usuarioEntity.get().getReviewsRecibidas().contains (reviewEntity.get()))
			 usuarioEntity.get().getReviewsRecibidas().add (reviewEntity.get());
		}
		log.info("Termina proceso de reemplazar las reviews del usuario con id = {0}", usuarioId);
		return usuarioEntity.get().getReviewsRecibidas();
	}

	@Transactional

	public void removeReviewRecibida(Long usuarioId, Long reviewId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar una review del srticulo con id = {0}", usuarioId);
		Optional<ReviewEntity> reviewEntity = reviewRepository.findById(reviewId);
		Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);

		if  (usuarioEntity.isEmpty())
			throw new EntityNotFoundException("El usuario no fue encontrado");

		if  (reviewEntity.isEmpty())
			throw new EntityNotFoundException("La review no fue encontrada");

	 usuarioEntity.get().getReviewsRecibidas().remove (reviewEntity.get());

		log.info("Termina proceso de borrar una review recibida del usuario con id = {0}", usuarioId);
	}

	@Transactional

	public void removeReviewEscrita(Long usuarioId, Long reviewId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar una review del srticulo con id = {0}", usuarioId);
		Optional<ReviewEntity> reviewEntity = reviewRepository.findById(reviewId);
		Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);

		if  (usuarioEntity.isEmpty())
			throw new EntityNotFoundException("El usuario no fue encontrado");

		if  (reviewEntity.isEmpty())
			throw new EntityNotFoundException("La review no fue encontrada");

	 usuarioEntity.get().getReviewsEscritas().remove (reviewEntity.get());

		log.info("Termina proceso de borrar una review recibida del usuario con id = {0}", usuarioId);
	}
    
}
