package com.example.TeamUp.Entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;
    private Integer reputacion;
    private Integer calificacion;

    @ManyToOne
    private UsuarioEntity usuario;

    @ManyToOne
    private MateriaEntity materia;
}
