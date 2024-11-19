package com.example.TeamUp.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDetailDTO extends UsuarioDTO {
    private List<ReviewDTO> reviewsEscritas = new ArrayList<>();
    private List<ReviewDTO> reviewsRecibidas = new ArrayList<>();
    private List<MateriaDTO> materias = new ArrayList<>();
}
