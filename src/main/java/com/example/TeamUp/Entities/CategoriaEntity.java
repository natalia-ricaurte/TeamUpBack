package com.example.TeamUp.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class CategoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private Float calificacion;

    @ManyToOne
    private ReviewEntity review;
}
