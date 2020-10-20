package dev.arielalvesdutra.ml.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

//// Entidades
// 1 Categoria.java
// 2 Usuario.java
// 3 ProdutoCaracteristiva.java
// 4 ProdutoImagem.java
// 5 ProdutoPergunta.java
// 6 ProdutoOpiniao.java

//// Loops
// 7 caracteristicas.forEach(caracteristica -> caracteristica.setProduto(this));

//// Função como parâmetro
// 8 .map(opiniao -> new BigDecimal(opiniao.getNota()))

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "{nome.naovazio}")
    private String nome;
    @Positive
    private BigDecimal valor;
    @NotNull(message = "{quantidade.obrigatoria}")
    @Positive
    private Integer quantidade;
    @Size(max = 1000, message = "Descrição deve ter até {max} caracteres!")
    @NotBlank(message = "Descrição é obrigatória!")
    private String descricao;
    private OffsetDateTime cadastradoEm = OffsetDateTime.now();

    private String url;

    @NotNull(message = "Categoria é obrigatória!")
    @ManyToOne
    private Categoria categoria;
    @NotNull(message = "Usuário é obrigatório!")
    @ManyToOne
    private Usuario usuario;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "produto", cascade = ALL, orphanRemoval = true)
    @Size(min = 3, message = "Mínimo de {min} características!")
    private Set<ProdutoCaracteristica> caracteristicas = new HashSet<>();
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "produto", orphanRemoval = true, cascade = ALL)
    private Set<ProdutoImagem> imagens;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "produto", cascade = ALL, orphanRemoval = true)
    private Set<ProdutoPergunta> perguntas;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "produto", cascade = ALL, orphanRemoval = true)
    private Set<ProdutoOpiniao> opinioes = new HashSet<>();

    public Produto() {
    }

    public Long getId() {
        return id;
    }

    public Produto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Produto setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Produto setValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Produto setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public Produto setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public OffsetDateTime getCadastradoEm() {
        return cadastradoEm;
    }

    public Produto setCadastradoEm(OffsetDateTime cadastradoEm) {
        this.cadastradoEm = cadastradoEm;
        return this;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Produto setCategoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public Set<ProdutoCaracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    /**
     * @todo REFATORAR
     */
    public Produto setCaracteristicas(Set<ProdutoCaracteristica> caracteristicas) {
        this.caracteristicas = caracteristicas;
        caracteristicas.forEach(caracteristica -> caracteristica.setProduto(this));
        return this;
    }

    public Set<ProdutoImagem> getImagens() {
        return imagens;
    }

    public Produto setImagens(Set<ProdutoImagem> imagens) {
        this.imagens = imagens;
        return this;
    }

    public Produto addImagem(ProdutoImagem produtoImagem) {
        this.imagens.add(produtoImagem);
        produtoImagem.setProduto(this);
        return this;
    }

    public Produto addPergunta(ProdutoPergunta produtoPergunta) {
        this.perguntas.add(produtoPergunta);
        produtoPergunta.setProduto(this);
        return this;
    }

    public Set<ProdutoPergunta> getPerguntas() {
        return perguntas;
    }

    public Produto setPerguntas(Set<ProdutoPergunta> perguntas) {
        this.perguntas = perguntas;
        return this;
    }

    public Set<ProdutoOpiniao> getOpinioes() {
        return opinioes;
    }

    public Produto setOpinioes(Set<ProdutoOpiniao> opinioes) {
        this.opinioes = opinioes;
        return this;
    }

    public Produto addOpiniao(ProdutoOpiniao produtoOpiniao) {
        this.opinioes.add(produtoOpiniao);
        produtoOpiniao.setProduto(this);
        return this;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Produto setUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public boolean naoPertenceAoUsuario(Usuario usuarioLogado) {
        return usuarioLogado == null
                || !usuarioLogado.equals(this.getUsuario());
    }

    public String getUrl() {
        return url;
    }

    public Produto setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Método que gera a URL do produto.
     */
    public void geraUrl() {
        setUrl(id + getNomeMinusculoSemEspacoComHifen());
    }

    public String getNomeMinusculoSemEspacoComHifen() {
        return nome.trim()
                .replace(" ", "-")
                .toLowerCase();
    }

    public Integer getQuantidadeDeOpinioes() {
        return opinioes.size();
    }

    public BigDecimal getMediaOpinioes() {
        var sum =  opinioes.stream()
                .map(opiniao -> new BigDecimal(opiniao.getNota()))
                .reduce(BigDecimal::add).get();

        return sum.divide(new BigDecimal(opinioes.size()));
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
