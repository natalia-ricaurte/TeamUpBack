package com.example.TeamUp.Entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String carrera;
    private String login;
    private String password;
    private Integer reputacion;
    private Integer calificacion;

}
