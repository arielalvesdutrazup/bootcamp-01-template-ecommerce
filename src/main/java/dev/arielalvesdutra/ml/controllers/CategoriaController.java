package dev.arielalvesdutra.ml.controllers;

import dev.arielalvesdutra.ml.controllers.dtos.CadastrarCategoriaRequestDTO;
import dev.arielalvesdutra.ml.entities.Categoria;
import dev.arielalvesdutra.ml.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * 1 CategoriaService.java
 * 2 Categoria.java
 * 3 CadastrarCategoriaRequestDTO.java
 */
@RequestMapping("/categorias")
@RestController
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<Long> cadastrar(
            UriComponentsBuilder uriBuilder,
            @Valid @RequestBody CadastrarCategoriaRequestDTO requestDTO) {

        Categoria categoriaCadastrada = categoriaService.cadastrar(requestDTO);

        URI uri = uriBuilder.path("/categorias/{id}")
                .buildAndExpand(categoriaCadastrada.getId())
                .toUri();

        return ResponseEntity.created(uri).body(categoriaCadastrada.getId());
    }
}
