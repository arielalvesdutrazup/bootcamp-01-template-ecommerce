package dev.arielalvesdutra.ml.tests.it.controllers;

import dev.arielalvesdutra.ml.controllers.dtos.*;
import dev.arielalvesdutra.ml.entities.Caracteristica;
import dev.arielalvesdutra.ml.entities.Categoria;
import dev.arielalvesdutra.ml.entities.Usuario;
import dev.arielalvesdutra.ml.services.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static dev.arielalvesdutra.ml.tests.factories.dtos.CadastraProdutoPerguntaRequestDTOFactory.perguntaParaPersisitr;
import static dev.arielalvesdutra.ml.tests.factories.dtos.CadastrarCaracteristicaRequestDTOFactory.*;
import static dev.arielalvesdutra.ml.tests.factories.dtos.CadastrarCategoriaRequestDTOFactory.eletronicos;
import static dev.arielalvesdutra.ml.tests.factories.dtos.CadastrarProdutoOpiniaoRequestDTOFactory.opiniaoValida;
import static dev.arielalvesdutra.ml.tests.factories.dtos.CadastrarProdutoRequestDTOFactory.paraPersistir;
import static dev.arielalvesdutra.ml.tests.factories.dtos.CadastrarUsuarioRequestDTOFactory.segundoUsuarioExemplo;
import static dev.arielalvesdutra.ml.tests.factories.dtos.CadastrarUsuarioRequestDTOFactory.usuarioExemplo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.POST;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class ProdutoControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CaracteristicaService caracteristicaService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;

    private Caracteristica peso;
    private Caracteristica altura;
    private Caracteristica largura;
    private Set<Long> caracteristicasIds = new HashSet<>();

    private Categoria categoria;

    private Usuario usuarioParaLogar;

    private String URL_BASE = "/produtos";

    @BeforeEach
    public void setUp() {
        categoria = categoriaService.cadastrar(eletronicos());
        peso = caracteristicaService.cadastrar(peso());
        altura = caracteristicaService.cadastrar(altura());
        largura = caracteristicaService.cadastrar(largura());

        caracteristicasIds.clear();
        caracteristicasIds.add(peso.getId());
        caracteristicasIds.add(altura.getId());
        caracteristicasIds.add(largura.getId());

        usuarioParaLogar = usuarioService.cadastrar(usuarioExemplo());
    }

    @AfterEach
    public void tearDown() {
        produtoService.removeTodos();
        categoriaService.removeTodos();
        caracteristicaService.removeTodos();
        usuarioService.removeTodos();
    }

    @Test
    public void cadastrar_deveRetornar201() {
        var requestDTO = paraPersistir(caracteristicasIds, categoria.getId());
        var httpEntity = new HttpEntity<CadastrarProdutoRequestDTO>(requestDTO, getCabelhoHttpValidoComAuthorization());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                URL_BASE,
                POST,
                httpEntity,
                String.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());

        var responseBody = responseEntity.getBody();

        assertThat(responseBody).isNotNull();

        var produtoId = Long.parseLong(responseBody);
        var produtoBuscado = produtoService.buscaPeloId(produtoId).get();

        assertThat(produtoBuscado).isNotNull();
        assertThat(produtoBuscado.getId()).isEqualTo(produtoId);
        assertThat(produtoBuscado.getNome()).isEqualTo(requestDTO.getNome());
        assertThat(produtoBuscado.getDescricao()).isEqualTo(requestDTO.getDescricao());
        assertThat(produtoBuscado.getValor()).isEqualTo(requestDTO.getValor());
        assertThat(produtoBuscado.getCategoria()).isEqualTo(categoria);
        assertThat(produtoBuscado.getCaracteristicas().size()).isEqualTo(3);
        assertThat(produtoBuscado.getCadastradoEm()).isNotNull();
        assertThat(produtoBuscado.getUrl()).isEqualTo(produtoBuscado.getId() + "mesa-de-madeira");
    }

    @Test
    public void cadastrar_semToken_deveRetornar401() {
        var requestDTO = paraPersistir(caracteristicasIds, categoria.getId());

        ResponseEntity<ErroResponseDTO> responseEntity = restTemplate.postForEntity(
                URL_BASE,
                requestDTO,
                ErroResponseDTO.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.UNAUTHORIZED.value());

        var responseBody = responseEntity.getBody();

        assertThat(responseBody.getMensagem()).isEqualTo("Usuário não autenticado!");
    }

    @Test
    public void cadastrar_semNome_deveRetornar400() {
        var requestDTO = paraPersistir(caracteristicasIds, categoria.getId());
        requestDTO.setNome(null);
        var httpEntity = new HttpEntity<CadastrarProdutoRequestDTO>(requestDTO, getCabelhoHttpValidoComAuthorization());

        ResponseEntity<ErroResponseDTO> responseEntity = restTemplate.exchange(
                URL_BASE,
                POST,
                httpEntity,
                ErroResponseDTO.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        var responseBody = responseEntity.getBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getMensagem()).isEqualTo("Dados inválidos!");
        assertThat(responseBody.getErros().contains("Nome não pode ser vazio!")).isTrue();
    }

    @Test
    public void detalhe_deveFuncionar() {
        var produtoCadastrado = produtoService.cadastrar(
                usuarioParaLogar.getId(),
                paraPersistir(caracteristicasIds, categoria.getId()));
        var url = URL_BASE + "/" + produtoCadastrado.getId();

        ResponseEntity<ProdutoDetalheResponseDTO> responseEntity = restTemplate.getForEntity(
                url,
                ProdutoDetalheResponseDTO.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());

        ProdutoDetalheResponseDTO responseDTO = responseEntity.getBody();

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getId()).isEqualTo(produtoCadastrado.getId());
        assertThat(responseDTO.getNome()).isEqualTo(produtoCadastrado.getNome());
        assertThat(responseDTO.getDescricao()).isEqualTo(produtoCadastrado.getDescricao());
        assertThat(responseDTO.getValor()).isEqualTo(produtoCadastrado.getValor());
        assertThat(responseDTO.getCadastradoEm()).isNotNull();
        assertThat(responseDTO.getCaracteristicas()).isEqualTo(ProdutoCaracteristicaResponseDTO.paraDTO(produtoCadastrado.getCaracteristicas()));
        assertThat(responseDTO.getCategoria()).isEqualTo(new CategoriaResponseDTO(produtoCadastrado.getCategoria()));
    }

    @Test
    public void cadastrarImagens_deveRetornar200() throws IOException {
        var produto = produtoService.cadastrar(usuarioParaLogar.getId(), paraPersistir(caracteristicasIds, categoria.getId()));
        var url = URL_BASE +  "/" + produto.getId() + "/imagens";

        HttpHeaders headers = getCabelhoHttpValidoComAuthorization();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("imagens", arquivoFake());
        body.add("imagens", arquivoFake());

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                url,
                POST,
                httpEntity,
                Void.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void cadastrarImagens_deUmProdutoDeOutroUsuario_deveRetornar403() throws IOException {
        var segundoUsuario = usuarioService.cadastrar(segundoUsuarioExemplo());
        var imagem1 = "";
        var produto = produtoService.cadastrar(usuarioParaLogar.getId(), paraPersistir( caracteristicasIds, categoria.getId()));
        var url = URL_BASE +  "/" + produto.getId() + "/imagens";
        HttpHeaders headers = getCabelhoHttpValidoComAuthorization(segundoUsuario);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("imagens", arquivoFake());
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);

        ResponseEntity<ErroResponseDTO> responseEntity = restTemplate.exchange(
                url,
                POST,
                httpEntity,
                ErroResponseDTO.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.FORBIDDEN.value());

        ErroResponseDTO responseDTO = responseEntity.getBody();

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getMensagem()).isEqualTo("Usuário não tem permissão para adicionar imagens nesse produto!");
    }

    @Test
    public void cadastrarImagens_semFoto_deveRetornar400() {
        var produto = produtoService.cadastrar(
                usuarioParaLogar.getId(),
                paraPersistir( caracteristicasIds, categoria.getId()));
        var url = URL_BASE +  "/" + produto.getId() + "/imagens";
        HttpHeaders headers = getCabelhoHttpValidoComAuthorization();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<ErroResponseDTO> responseEntity = restTemplate.exchange(
                url,
                POST,
                httpEntity,
                ErroResponseDTO.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        ErroResponseDTO responseDTO = responseEntity.getBody();

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getMensagem()).isEqualTo("Dados inválidos!");
        assertThat(responseDTO.getErros()).asString().contains("É obrigatório enviar imagens!");
        assertThat(responseDTO.getErros()).asString().contains("É obrigatório ter uma imagem e não ter imagens vazias!");
    }

    @Test
    public void cadastrarImagens_comChaveImagensVazia_deveRetornar400() {
        var produto = produtoService.cadastrar(
                usuarioParaLogar.getId(),
                paraPersistir( caracteristicasIds, categoria.getId()));
        var url = URL_BASE +  "/" + produto.getId() + "/imagens";
        HttpHeaders headers = getCabelhoHttpValidoComAuthorization();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("imagens", null);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);

        ResponseEntity<ErroResponseDTO> responseEntity = restTemplate.exchange(
                url,
                POST,
                httpEntity,
                ErroResponseDTO.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        ErroResponseDTO responseDTO = responseEntity.getBody();

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getMensagem()).isEqualTo("Dados inválidos!");
        assertThat(responseDTO.getErros()).asString().contains("É obrigatório enviar imagens!");
        assertThat(responseDTO.getErros()).asString().contains("É obrigatório ter uma imagem e não ter imagens vazias!");
    }

    @Test
    public void cadastrarOpiniao_deveRetornar200() {
        var requestDTO = opiniaoValida();
        var produto = produtoService.cadastrar(
                usuarioParaLogar.getId(),
                paraPersistir( caracteristicasIds, categoria.getId()));
        var url = URL_BASE + "/" + produto.getId() + "/opinioes";
        var httpEntity = new HttpEntity<CadastrarProdutoOpiniaoRequestDTO>(requestDTO, getCabelhoHttpValidoComAuthorization());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                POST,
                httpEntity,
                String.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());

        var responseBody = responseEntity.getBody();

        assertThat(responseBody).isNotNull();

        var opiniaoId = Long.parseLong(responseBody);
        var opiniaoBuscada = produtoService.buscaOpiniaoPeloId(opiniaoId);

        assertThat(opiniaoBuscada).isNotNull();
        assertThat(opiniaoBuscada.getId()).isEqualTo(opiniaoId);
        assertThat(opiniaoBuscada.getNota()).isEqualTo(requestDTO.getNota());
        assertThat(opiniaoBuscada.getTitulo()).isEqualTo(requestDTO.getTitulo());
        assertThat(opiniaoBuscada.getDescricao()).isEqualTo(requestDTO.getDescricao());
        assertThat(opiniaoBuscada.getCadastradoEm()).isNotNull();
    }

    @Test
    public void cadastrarOpiniao_comNotaMenorQue1_deveRetornar400() {
        var requestDTO = opiniaoValida();
        requestDTO.setNota(0);
        var produto = produtoService.cadastrar(
                usuarioParaLogar.getId(),
                paraPersistir( caracteristicasIds, categoria.getId()));
        var url = URL_BASE + "/" + produto.getId() + "/opinioes";
        var httpEntity = new HttpEntity<CadastrarProdutoOpiniaoRequestDTO>(requestDTO, getCabelhoHttpValidoComAuthorization());

        ResponseEntity<ErroResponseDTO> responseEntity = restTemplate.exchange(
                url,
                POST,
                httpEntity,
                ErroResponseDTO.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        ErroResponseDTO responseDTO = responseEntity.getBody();

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getMensagem()).isEqualTo("Dados inválidos!");
        assertThat(responseDTO.getErros()).asString().contains("Nota mínima é de 1!");
    }

    @Test
    public void cadastrarOpiniao_comNotaMaiorQue5_deveRetornar400() {
        var requestDTO = opiniaoValida();
        requestDTO.setNota(6);
        var produto = produtoService.cadastrar(
                usuarioParaLogar.getId(),
                paraPersistir( caracteristicasIds, categoria.getId()));
        var url = URL_BASE + "/" + produto.getId() + "/opinioes";
        var httpEntity = new HttpEntity<CadastrarProdutoOpiniaoRequestDTO>(requestDTO, getCabelhoHttpValidoComAuthorization());

        ResponseEntity<ErroResponseDTO> responseEntity = restTemplate.exchange(
                url,
                POST,
                httpEntity,
                ErroResponseDTO.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        ErroResponseDTO responseDTO = responseEntity.getBody();

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getMensagem()).isEqualTo("Dados inválidos!");
        assertThat(responseDTO.getErros()).asString().contains("Nota máxima é de 5!");
    }

    @Test
    public void cadastrarPergunta_deveRetornar200() {
        CadastraProdutoPerguntaRequestDTO requestDTO = perguntaParaPersisitr();
        var produto = produtoService.cadastrar(
                usuarioParaLogar.getId(),
                paraPersistir(caracteristicasIds, categoria.getId()));
        var url = URL_BASE +  "/" + produto.getId() + "/perguntas";
        var httpEntity = new HttpEntity<CadastraProdutoPerguntaRequestDTO>(requestDTO, getCabelhoHttpValidoComAuthorization());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                POST,
                httpEntity,
                String.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());

        var responseBody = responseEntity.getBody();

        assertThat(responseBody).isNotNull();

        var perguntaId = Long.parseLong(responseBody);
        var perguntaBuscada = produtoService.buscaPerguntaPeloId(perguntaId);

        assertThat(perguntaBuscada).isNotNull();
        assertThat(perguntaBuscada.getId()).isEqualTo(perguntaId);
        assertThat(perguntaBuscada.getTitulo()).isEqualTo(requestDTO.getTitulo());
        assertThat(perguntaBuscada.getCadastradoEm()).isNotNull();
    }

    @Test
    public void cadastrarPergunta_semTitulo_deveRetornar400() {
        var requestDTO = perguntaParaPersisitr();
        requestDTO.setTitulo(null);
        var produto = produtoService.cadastrar(
                usuarioParaLogar.getId(),
                paraPersistir(caracteristicasIds, categoria.getId()));
        var url = URL_BASE +  "/" + produto.getId() + "/perguntas";
        var httpEntity = new HttpEntity<CadastraProdutoPerguntaRequestDTO>(requestDTO, getCabelhoHttpValidoComAuthorization());

        ResponseEntity<ErroResponseDTO> responseEntity = restTemplate.exchange(
                url,
                POST,
                httpEntity,
                ErroResponseDTO.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        ErroResponseDTO responseDTO = responseEntity.getBody();

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getMensagem()).isEqualTo("Dados inválidos!");
        assertThat(responseDTO.getErros()).asString().contains("Título é obrigatório!");
    }

    /**
     * @todo REFATORAR
     */
    private HttpHeaders getCabelhoHttpValidoComAuthorization() {
        var httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", getTokenComUsuarioValido(usuarioParaLogar));
        return httpHeaders;
    }

    /**
     * @todo REFATORAR
     */
    private HttpHeaders getCabelhoHttpValidoComAuthorization(Usuario usuario) {
        var httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", getTokenComUsuarioValido(usuario));
        return httpHeaders;
    }

    /**
     * @todo REFATORAR
     */
    private String getTokenComUsuarioValido(Usuario usuario) {
        return "Bearer " + tokenService.geraToken(usuario);
    }

    /**
     * @todo REFATORAR
     */
    private Resource arquivoFake() throws IOException {
        Path arquivoTemporario = Files.createTempFile("arquivo_fake_para_testes", ".png");
        Files.write(arquivoTemporario, "Gerando conteudo para o arquivo".getBytes());
        File arquivo = arquivoTemporario.toFile();

        return new FileSystemResource(arquivo);
    }
}
