package dev.arielalvesdutra.ml.controllers;

import dev.arielalvesdutra.ml.controllers.dtos.CadastrarUsuarioRequestDTO;
import dev.arielalvesdutra.ml.entities.Usuario;
import dev.arielalvesdutra.ml.services.UsuarioService;
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
 * 1 UsuarioService.java
 * 2 Usuario.java
 * 3 CadastrarUsuarioRequestDTO.java
 */
@RequestMapping("/usuarios")
@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Long> cadastrar(
            UriComponentsBuilder uriBuilder,
            @Valid @RequestBody CadastrarUsuarioRequestDTO requestDTO) {

        Usuario usuarioCadastrado = usuarioService.cadastrar(requestDTO);

        URI uri = uriBuilder.path("/usuarios/{id}")
                .buildAndExpand(usuarioCadastrado.getId())
                .toUri();

        return ResponseEntity.created(uri).body(usuarioCadastrado.getId());
    }
}
