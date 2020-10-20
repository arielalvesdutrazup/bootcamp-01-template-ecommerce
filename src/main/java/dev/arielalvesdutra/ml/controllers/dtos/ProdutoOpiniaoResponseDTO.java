package dev.arielalvesdutra.ml.controllers.dtos;

import dev.arielalvesdutra.ml.entities.ProdutoOpiniao;

import java.time.OffsetDateTime;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ProdutoOpiniaoResponseDTO {

    private Long id;
    private String titulo;
    private String descricacao;
    private Integer nota;
    private OffsetDateTime cadastradoEm;
    private UsuarioResponseDTO usuario;

    public ProdutoOpiniaoResponseDTO() {}

    public ProdutoOpiniaoResponseDTO(ProdutoOpiniao produtoOpiniao) {
        setId(produtoOpiniao.getId());
        setTitulo(produtoOpiniao.getTitulo());
        setDescricacao(produtoOpiniao.getDescricao());
        setNota(produtoOpiniao.getNota());
        setUsuario(new UsuarioResponseDTO(produtoOpiniao.getUsuario()));
        setCadastradoEm(produtoOpiniao.getCadastradoEm());
    }

    public Long getId() {
        return id;
    }

    public ProdutoOpiniaoResponseDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitulo() {
        return titulo;
    }

    public ProdutoOpiniaoResponseDTO setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public String getDescricacao() {
        return descricacao;
    }

    public ProdutoOpiniaoResponseDTO setDescricacao(String descricacao) {
        this.descricacao = descricacao;
        return this;
    }

    public Integer getNota() {
        return nota;
    }

    public ProdutoOpiniaoResponseDTO setNota(Integer nota) {
        this.nota = nota;
        return this;
    }

    public OffsetDateTime getCadastradoEm() {
        return cadastradoEm;
    }

    public ProdutoOpiniaoResponseDTO setCadastradoEm(OffsetDateTime cadastradoEm) {
        this.cadastradoEm = cadastradoEm;
        return this;
    }

    public UsuarioResponseDTO getUsuario() {
        return usuario;
    }

    public ProdutoOpiniaoResponseDTO setUsuario(UsuarioResponseDTO usuario) {
        this.usuario = usuario;
        return this;
    }

    public static Set<ProdutoOpiniaoResponseDTO> paraDTO(Set<ProdutoOpiniao> opinioes) {
        return opinioes.stream()
                .map(ProdutoOpiniaoResponseDTO::new)
                .collect(toSet());
    }
}
