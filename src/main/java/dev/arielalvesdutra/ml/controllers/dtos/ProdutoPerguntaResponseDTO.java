package dev.arielalvesdutra.ml.controllers.dtos;

import dev.arielalvesdutra.ml.entities.ProdutoPergunta;

import java.time.OffsetDateTime;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ProdutoPerguntaResponseDTO {

    private Long id;
    private String titulo;
    private OffsetDateTime cadastradoEm;
    private UsuarioResponseDTO usuario;

    public ProdutoPerguntaResponseDTO() {}

    public ProdutoPerguntaResponseDTO(ProdutoPergunta produtoPergunta) {
        setId(produtoPergunta.getId());
        setTitulo(produtoPergunta.getTitulo());
        setCadastradoEm(cadastradoEm = produtoPergunta.getCadastradoEm());
        setUsuario(new UsuarioResponseDTO(produtoPergunta.getUsuario()));
    }

    public Long getId() {
        return id;
    }

    public ProdutoPerguntaResponseDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitulo() {
        return titulo;
    }

    public ProdutoPerguntaResponseDTO setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public OffsetDateTime getCadastradoEm() {
        return cadastradoEm;
    }

    public ProdutoPerguntaResponseDTO setCadastradoEm(OffsetDateTime cadastradoEm) {
        this.cadastradoEm = cadastradoEm;
        return this;
    }

    public UsuarioResponseDTO getUsuario() {
        return usuario;
    }

    public ProdutoPerguntaResponseDTO setUsuario(UsuarioResponseDTO usuario) {
        this.usuario = usuario;
        return this;
    }

    public static Set<ProdutoPerguntaResponseDTO> paraDTO(Set<ProdutoPergunta> perguntas) {
        return perguntas.stream()
                .map(ProdutoPerguntaResponseDTO::new)
                .collect(toSet());
    }
}
