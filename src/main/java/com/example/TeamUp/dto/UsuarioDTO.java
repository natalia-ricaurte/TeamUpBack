package com.example.TeamUp.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter

public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String carrera;
    private String email;
    private String password;
    private Integer edad;
    private List<String> habilidades;
    private List<MateriaDTO> materiasIds;  
    private String action; 
    private List<ReviewDTO> reviewsRecibidas;
}
