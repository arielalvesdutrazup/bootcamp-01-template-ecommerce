package dev.arielalvesdutra.ml.tests.it.controllers;


import dev.arielalvesdutra.ml.services.UsuarioService;
import dev.arielalvesdutra.ml.tests.factories.dtos.CadastrarUsuarioRequestDTOFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class UsuarioControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UsuarioService usuarioService;

    private final String URL_BASE = "/usuarios";

    @AfterEach
    public void tearDown() {
        usuarioService.removeTodos();
    }

    @Test
    public void cadastrar_deveRetornar201() {
        var bcrypt = new BCryptPasswordEncoder();
        var requestDTO = CadastrarUsuarioRequestDTOFactory.paraPersistir();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                URL_BASE,
                requestDTO,
                String.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());

        var responseBody = responseEntity.getBody();

        assertThat(responseBody).isNotNull();

        var usuarioId = Long.parseLong(responseBody);
        var usuarioBuscado = usuarioService.buscarPeloId(usuarioId);

        assertThat(usuarioBuscado).isNotNull();
        assertThat(usuarioBuscado.getId()).isEqualTo(usuarioId);
        assertThat(usuarioBuscado.getLogin()).isEqualTo(requestDTO.getLogin());
        assertThat(usuarioBuscado.getSenha()).isNotNull();
        assertThat(usuarioBuscado.getSenha()).isNotEqualTo(requestDTO.getSenha());
        assertThat(bcrypt.matches(requestDTO.getSenha(), usuarioBuscado.getSenha())).isTrue();
        assertThat(usuarioBuscado.getCadastradoEm()).isNotNull();
    }

}
