package dev.arielalvesdutra.ml.controllers;

import dev.arielalvesdutra.ml.controllers.dtos.*;
import dev.arielalvesdutra.ml.entities.Produto;
import dev.arielalvesdutra.ml.entities.ProdutoOpiniao;
import dev.arielalvesdutra.ml.entities.ProdutoPergunta;
import dev.arielalvesdutra.ml.services.ProdutoService;
import dev.arielalvesdutra.ml.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

// 1 ProdutoService.java
// 2 CadastrarProdutoRequestDTO.java
// 3 Produto.java
// 4 TokenService.java
// 5 ProdutoPergunta.java
// 6 ProdutoDetalheResponseDTO.java
// 7 CadastrarProdutoImagensRequestDTO.java
// 8 CadastrarProdutoOpiniaoRequestDTO.java
// 9 CadastrarProdutoPerguntaRequestDTO.java
// 10 ProdutoOpiniao.java
@RequestMapping("/produtos")
@RestController
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<Long> cadastrar(
            UriComponentsBuilder uriBuilder,
            @RequestHeader (name="Authorization") String token,
            @Valid @RequestBody CadastrarProdutoRequestDTO requestDTO) {

        Long usuarioId = tokenService.getUsuarioIdDoCabecalho(token);
        Produto produto = produtoService.cadastrar(usuarioId, requestDTO);

        URI uri = uriBuilder.path("/produtos/{id}")
                .buildAndExpand(produto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(produto.getId());
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<ProdutoDetalheResponseDTO> detalhe(@PathVariable Long produtoId) {
        Produto produto = produtoService.buscaDetalhePeloId(produtoId).get();

        return ResponseEntity.ok().body(new ProdutoDetalheResponseDTO(produto));
    }

    @PostMapping("/{produtoId}/imagens")
    public ResponseEntity<Void> cadastrarImagens(
            @RequestHeader (name="Authorization") String token,
            @PathVariable Long produtoId,
            @Valid CadastrarProdutoImagensRequestDTO requestDTO) {

        Long usuarioId = tokenService.getUsuarioIdDoCabecalho(token);
        produtoService.cadastrarImagens(usuarioId, produtoId, requestDTO.getImagens());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{produtoId}/perguntas")
    public ResponseEntity<Long> cadastrarPergunta(
            @RequestHeader (name="Authorization") String token,
            @PathVariable Long produtoId,
            @Valid @RequestBody CadastraProdutoPerguntaRequestDTO requestDTO){

        Long usuarioId = tokenService.getUsuarioIdDoCabecalho(token);
        ProdutoPergunta produtoPergunta = produtoService.cadastrarPergunta(usuarioId, produtoId, requestDTO);

        return ResponseEntity.ok().body(produtoPergunta.getId());
    }

    @PostMapping("/{produtoId}/opinioes")
    public ResponseEntity<Long> cadastrarOpiniao(
            @RequestHeader (name="Authorization") String token,
            @PathVariable Long produtoId,
            @Valid @RequestBody CadastrarProdutoOpiniaoRequestDTO requestDTO){

        Long usuarioId = tokenService.getUsuarioIdDoCabecalho(token);
        ProdutoOpiniao produtoOpiniao = produtoService.cadastrarOpiniao(usuarioId, produtoId, requestDTO);

        return ResponseEntity.ok().body(produtoOpiniao.getId());
    }
}
