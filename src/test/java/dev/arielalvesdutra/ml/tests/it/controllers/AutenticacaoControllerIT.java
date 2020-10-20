package dev.arielalvesdutra.ml.tests.it.controllers;

import dev.arielalvesdutra.ml.controllers.dtos.ErroResponseDTO;
import dev.arielalvesdutra.ml.controllers.dtos.LoginRequestDTO;
import dev.arielalvesdutra.ml.controllers.dtos.TokenResponseDTO;
import dev.arielalvesdutra.ml.entities.Usuario;
import dev.arielalvesdutra.ml.services.UsuarioService;
import dev.arielalvesdutra.ml.tests.factories.dtos.LoginRequestDTOFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static dev.arielalvesdutra.ml.tests.factories.dtos.CadastrarUsuarioRequestDTOFactory.paraPersistir;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class AutenticacaoControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UsuarioService usuarioService;

    private final String URL_BASE = "/autenticacao";

    private Usuario usuarioParaLogar;

    @BeforeEach
    public void setUp() {
        usuarioParaLogar = usuarioService.cadastrar(paraPersistir());
    }

    @AfterEach
    public void tearDown() {
        usuarioService.removeTodos();
    }

    @Test
    public void login_comUsuarioValido_deveRetornarTokenEStatus200() {
        var  requestDTO = LoginRequestDTOFactory.loginValido();

        ResponseEntity<TokenResponseDTO> responseEntity = restTemplate.postForEntity(
                URL_BASE,
                requestDTO,
                TokenResponseDTO.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());

        var responseBody = responseEntity.getBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getToken()).isNotEmpty();
    }

    @Test
    public void login_comUsuarioInvalido_deveRetornar401() {
        var requestDTO = new LoginRequestDTO()
                .setLogin("login@naoexistente.com")
                .setSenha("umasenha1aqui");

        ResponseEntity<ErroResponseDTO> responseEntity = restTemplate.postForEntity(
                URL_BASE,
                requestDTO,
                ErroResponseDTO.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.UNAUTHORIZED.value());

        var responseDTO = responseEntity.getBody();

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getMensagem()).isEqualTo("Dados de autenticação inválidos!");
    }
}
