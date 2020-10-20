package dev.arielalvesdutra.ml.tests.it.controllers;


import dev.arielalvesdutra.ml.services.CategoriaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static dev.arielalvesdutra.ml.tests.factories.dtos.CadastrarCategoriaRequestDTOFactory.paraPersistir;
import static dev.arielalvesdutra.ml.tests.factories.dtos.CadastrarCategoriaRequestDTOFactory.paraPersitir2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class CategoriaControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CategoriaService categoriaService;

    private final String URL_BASE = "/categorias";

    @AfterEach
    public void tearDown() {
        categoriaService.removeTodos();
    }

    @Test
    public void cadastrar_deveRetornar201() {
        var requestDTO = paraPersistir();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL_BASE, requestDTO, String.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());

        var responseBody = responseEntity.getBody();

        assertThat(responseBody).isNotNull();

        var categoriaId = Long.parseLong(responseBody);
        var categoriaBuscada = categoriaService.buscaPeloId(categoriaId);

        assertThat(categoriaBuscada).isNotNull();
        assertThat(categoriaBuscada.getId()).isEqualTo(categoriaId);
        assertThat(categoriaBuscada.getNome()).isEqualTo(requestDTO.getNome());
        assertThat(categoriaBuscada.getCadastradoEm()).isNotNull();
    }

    @Test
    public void cadastrar_comCategoriaMae_deveRetornar201() {
        var categoriaMae = categoriaService.cadastrar(paraPersistir());
        var requestDTO = paraPersitir2(categoriaMae.getId());

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                URL_BASE,
                requestDTO,
                String.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());

        var responseBody = responseEntity.getBody();

        assertThat(responseBody).isNotNull();

        var categoriaId = Long.parseLong(responseBody);
        var categoriaBuscada = categoriaService.buscaPeloId(categoriaId);

        assertThat(categoriaBuscada).isNotNull();
        assertThat(categoriaBuscada.getId()).isEqualTo(categoriaId);
        assertThat(categoriaBuscada.getNome()).isEqualTo(requestDTO.getNome());
        assertThat(categoriaBuscada.getCategoriaMae()).isEqualTo(categoriaMae);
        assertThat(categoriaBuscada.getCadastradoEm()).isNotNull();
    }
}
