package com.example.TeamUp.Repositories;
import com.example.TeamUp.Entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    

    UsuarioEntity findByLogin(String login);
    UsuarioEntity findByEmail(String email);

}
