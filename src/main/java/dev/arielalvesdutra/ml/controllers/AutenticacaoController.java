package dev.arielalvesdutra.ml.controllers;

import dev.arielalvesdutra.ml.controllers.dtos.LoginRequestDTO;
import dev.arielalvesdutra.ml.controllers.dtos.TokenResponseDTO;
import dev.arielalvesdutra.ml.services.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

// 1 LoginRequestDTO.java
// 2 TokenResponseDTO.java
// 3 AutenticacaoService.java
@RequestMapping("/autenticacao")
@RestController
public class AutenticacaoController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @PostMapping
    public ResponseEntity<TokenResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO requestDTO) {

        String token = autenticacaoService.autentica(requestDTO);

        return ResponseEntity.ok().body(new TokenResponseDTO(token));
    }
}
