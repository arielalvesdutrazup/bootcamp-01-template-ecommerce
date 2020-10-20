package dev.arielalvesdutra.ml.controllers.dtos;

import dev.arielalvesdutra.ml.entities.Produto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

public class ProdutoDetalheResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal valor;
    private OffsetDateTime cadastradoEm;

    private CategoriaResponseDTO categoria;

    private Set<ProdutoCaracteristicaResponseDTO> caracteristicas;
    private Set<ProdutoOpiniaoResponseDTO> opinioes;
    private Set<ProdutoPerguntaResponseDTO> perguntas;

    private BigDecimal notasMedia;
    private Integer notasQuantidade;

    public ProdutoDetalheResponseDTO() {}

    public ProdutoDetalheResponseDTO(Produto produto) {
        setId(produto.getId());
        setNome(produto.getNome());
        setDescricao(produto.getDescricao());
        setValor(produto.getValor());
        setCadastradoEm(produto.getCadastradoEm());

        setCategoria(new CategoriaResponseDTO(produto.getCategoria()));

        setCaracteristicas(ProdutoCaracteristicaResponseDTO.paraDTO(produto.getCaracteristicas()));
        setPerguntas(ProdutoPerguntaResponseDTO.paraDTO(produto.getPerguntas()));

        if (!produto.getOpinioes().isEmpty()) {
            setOpinioes(ProdutoOpiniaoResponseDTO.paraDTO(produto.getOpinioes()));
            setNotasQuantidade(produto.getQuantidadeDeOpinioes());
            setNotasMedia(produto.getMediaOpinioes());
        }
    }

    public Long getId() {
        return id;
    }

    public ProdutoDetalheResponseDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public ProdutoDetalheResponseDTO setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public ProdutoDetalheResponseDTO setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public ProdutoDetalheResponseDTO setValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public BigDecimal getNotasMedia() {
        return notasMedia;
    }

    public ProdutoDetalheResponseDTO setNotasMedia(BigDecimal notasMedia) {
        this.notasMedia = notasMedia;
        return this;
    }

    public Integer getNotasQuantidade() {
        return notasQuantidade;
    }

    public ProdutoDetalheResponseDTO setNotasQuantidade(Integer notasQuantidade) {
        this.notasQuantidade = notasQuantidade;
        return this;
    }

    public Set<ProdutoCaracteristicaResponseDTO> getCaracteristicas() {
        return caracteristicas;
    }

    public ProdutoDetalheResponseDTO setCaracteristicas(Set<ProdutoCaracteristicaResponseDTO> caracteristicas) {
        this.caracteristicas = caracteristicas;
        return this;
    }

    public Set<ProdutoOpiniaoResponseDTO> getOpinioes() {
        return opinioes;
    }

    public ProdutoDetalheResponseDTO setOpinioes(Set<ProdutoOpiniaoResponseDTO> opinioes) {
        this.opinioes = opinioes;
        return this;
    }

    public Set<ProdutoPerguntaResponseDTO> getPerguntas() {
        return perguntas;
    }

    public ProdutoDetalheResponseDTO setPerguntas(Set<ProdutoPerguntaResponseDTO> perguntas) {
        this.perguntas = perguntas;
        return this;
    }

    public OffsetDateTime getCadastradoEm() {
        return cadastradoEm;
    }

    public ProdutoDetalheResponseDTO setCadastradoEm(OffsetDateTime cadastradoEm) {
        this.cadastradoEm = cadastradoEm;
        return this;
    }

    public CategoriaResponseDTO getCategoria() {
        return categoria;
    }

    public ProdutoDetalheResponseDTO setCategoria(CategoriaResponseDTO categoria) {
        this.categoria = categoria;
        return this;
    }
}
