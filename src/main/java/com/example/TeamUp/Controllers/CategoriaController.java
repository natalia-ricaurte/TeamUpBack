package com.example.TeamUp.Controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.TeamUp.Entities.CategoriaEntity;
import com.example.TeamUp.Services.CategoriaService;
import com.example.TeamUp.dto.CategoriaDTO;
import com.example.TeamUp.dto.CategoriaDetailDTO;
import com.example.TeamUp.exceptions.EntityNotFoundException;
import com.example.TeamUp.exceptions.IllegalOperationException;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private final CategoriaService categoriaService;

    @Autowired
    private ModelMapper modelMapper;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Obtener todas las categorías
    @GetMapping
    public List<CategoriaDetailDTO> getCategorias() throws IllegalOperationException {
        List<CategoriaEntity> categorias = categoriaService.getCategorias();
        return modelMapper.map(categorias, new TypeToken<List<CategoriaDetailDTO>>() {}.getType());
    }

    // Obtener categoría por id
    @GetMapping("/{id}")
    public CategoriaDetailDTO getCategoria(@PathVariable Long id) throws EntityNotFoundException {
        Optional<CategoriaEntity> categoria = categoriaService.getCategoriaById(id);
        return modelMapper.map(categoria, CategoriaDetailDTO.class);
    }

    // Crear una categoría
    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    public CategoriaDTO create(@RequestBody CategoriaDTO categoriaDTO) throws IllegalOperationException, EntityNotFoundException {
        CategoriaEntity categoriaEntity = categoriaService.saveCategoria(modelMapper.map(categoriaDTO, CategoriaEntity.class));
        return modelMapper.map(categoriaEntity, CategoriaDTO.class);
    }

    // Eliminar una categoría
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        categoriaService.deleteCategoria(id);
    }
}
