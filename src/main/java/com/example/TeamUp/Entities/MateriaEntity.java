package com.example.TeamUp.Entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class MateriaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne
    private UsuarioEntity usuario;

}
