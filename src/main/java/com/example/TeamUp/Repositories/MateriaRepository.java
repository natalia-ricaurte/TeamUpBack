package com.example.TeamUp.Repositories;
import com.example.TeamUp.Entities.MateriaEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepository extends JpaRepository<MateriaEntity, Long> {
    Optional<MateriaEntity> findByNombre(String nombre);
}
