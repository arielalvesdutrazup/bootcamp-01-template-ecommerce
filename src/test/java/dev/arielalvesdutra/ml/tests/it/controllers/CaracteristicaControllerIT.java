package dev.arielalvesdutra.ml.tests.it.controllers;

import dev.arielalvesdutra.ml.controllers.dtos.ErroResponseDTO;
import dev.arielalvesdutra.ml.services.CaracteristicaService;
import dev.arielalvesdutra.ml.tests.factories.dtos.CadastrarCaracteristicaRequestDTOFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class CaracteristicaControllerIT {

    @Autowired
    private CaracteristicaService caracteristicaService;

    @Autowired
    private TestRestTemplate restTemplate;

    private String URL_BASE = "/caracteristicas";

    @AfterEach
    public void tearDown() {
        caracteristicaService.removeTodos();
    }

    @Test
    public void cadastrar_deveRetornar201() {
        var requestDTO = CadastrarCaracteristicaRequestDTOFactory.paraPersistir();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                URL_BASE,
                requestDTO,
                String.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());

        var responseBody = responseEntity.getBody();

        assertThat(responseBody).isNotNull();

        var caracteristicaId = Long.parseLong(responseBody);
        var caracteristicaBuscada = caracteristicaService.buscaPeloId(caracteristicaId);

        assertThat(caracteristicaBuscada).isNotNull();
        assertThat(caracteristicaBuscada.getId()).isEqualTo(caracteristicaId);
        assertThat(caracteristicaBuscada.getNome()).isEqualTo(requestDTO.getNome());
        assertThat(caracteristicaBuscada.getCadastradoEm()).isNotNull();
    }

    @Test
    public void cadastrar_semNome_deveRetornar400() {
        var requestDTO = CadastrarCaracteristicaRequestDTOFactory.paraPersistir();
        requestDTO.setNome(null);

        ResponseEntity<ErroResponseDTO> responseEntity = restTemplate.postForEntity(
                URL_BASE,
                requestDTO,
                ErroResponseDTO.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        var responseBody = responseEntity.getBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getMensagem()).isEqualTo("Dados inválidos!");
        assertThat(responseBody.getErros().contains("Nome não pode ser vazio!")).isTrue();
    }
}
