package dev.arielalvesdutra.ml.services;

import dev.arielalvesdutra.ml.controllers.dtos.CadastraProdutoPerguntaRequestDTO;
import dev.arielalvesdutra.ml.controllers.dtos.CadastrarProdutoOpiniaoRequestDTO;
import dev.arielalvesdutra.ml.controllers.dtos.CadastrarProdutoRequestDTO;
import dev.arielalvesdutra.ml.controllers.dtos.ProdutoCaracteristicaRequestDTO;
import dev.arielalvesdutra.ml.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


// Services
// 3 CategoriaService.java
// 4 ImagemService.java
// 5 UsuarioService.java
// 6 CaracteristicasService.java
// 7 EmailService.java

// DTOs
// 2 CadastrarProdutoRequestDTO.java
// 12 CadastrarProdutoOpiniaoRequestDTO.java
// 15 ProdutoCaracteristicaRequestDTO

// Entidades
// 1 Produto.java
// 11 ProdutoImagem.java
// 13 ProdutoOpiniao.java
// 14 ProdutoPergunta.java
// 8 ProdutoCaracteristica.java


// Loops
// 10 imagens.forEach(imagem -> {
// 16 caracteristicas.forEach(caracteristicaDTO -> {

// Condicionais
// 9 if (produto.naoPertenceAoUsuario(usuarioLogado))
// 17 if (produtoLista.size() == 0)

@Service
public class ProdutoService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ImagemService imagemService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CaracteristicaService caracteristicaService;

    @Autowired
    private EmailService emailService;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Busca completa do produtos, com todas as relações do produto.
     * <p>
     * Retorna o detalhe do produto;
     *
     * @param produtoId
     * @return
     */
    public Optional<Produto> buscaDetalhePeloId(Long produtoId) {

        List<Produto> produtos = entityManager.createQuery(
                "SElECT o " +
                        "FROM " + Produto.class.getName() + " o " +
                        "LEFT JOIN o.categoria " +
                        "LEFT JOIN o.caracteristicas " +
                        "LEFT JOIN o.perguntas " +
                        "LEFT JOIN o.opinioes " +
                        "LEFT JOIN o.imagens " +
                        "WHERE o.id = :id", Produto.class)
                .setParameter("id", produtoId)
                .getResultList();

        if (produtos == null) {
            return null;
        }

        return Optional.ofNullable(produtos.get(0));
    }

    public Optional<Produto> buscaPeloId(Long produtoId) {
        Assert.notNull(produtoId, "Id para busca do produto não pode ser nulo!");

        List<Produto> resultado = entityManager.createQuery(
                "SELECT DISTINCT o " +
                        "FROM " + Produto.class.getName() + " o " +
                        "LEFT JOIN FETCH o.caracteristicas " +
                        "WHERE o.id = :id", Produto.class)
                .setParameter("id", produtoId)
                .getResultList();

        return this.retornaPrimeiroRegistroDaLista(resultado, "id");
    }

    public ProdutoPergunta buscaPerguntaPeloId(Long perguntaId) {
        Assert.notNull(perguntaId, "Id para busca do produto não pode ser nulo!");

        return entityManager.find(ProdutoPergunta.class, perguntaId);
    }

    public ProdutoOpiniao buscaOpiniaoPeloId(Long opiniaoId) {
        return entityManager.find(ProdutoOpiniao.class, opiniaoId);
    }

    @Transactional
    public Produto cadastrar(Long usuarioId, CadastrarProdutoRequestDTO requestDTO) {
        Usuario usuario = usuarioService.buscaPeloId(usuarioId);

        Produto produto = new Produto()
                .setUsuario(usuario)
                .setNome(requestDTO.getNome())
                .setDescricao(requestDTO.getDescricao())
                .setQuantidade(requestDTO.getQuantidade())
                .setValor(requestDTO.getValor())
                .setCategoria(categoriaService.buscaPeloId(requestDTO.getCategoriaId()));
        Set<ProdutoCaracteristica> produtoCaracteristicas = buscaCaracteristicas(requestDTO.getCaracteristicas());

        produto.setCaracteristicas(produtoCaracteristicas);

        entityManager.persist(produto);

        produto.geraUrl();

        return produto;
    }

    @Transactional
    public void cadastrarImagens(
            Long usuarioId,
            Long produtoId,
            List<MultipartFile> imagens) {
        Usuario usuarioLogado = usuarioService.buscaPeloId(usuarioId);
        Produto produto = buscaPeloId(produtoId).get();

        if (produto.naoPertenceAoUsuario(usuarioLogado))
            throw new AccessDeniedException("Usuário não tem permissão para adicionar imagens nesse produto!");

        imagens.forEach(imagem -> {
            String imagemUrl = imagemService.armazenar(imagem);
            ProdutoImagem produtoImagem = new ProdutoImagem()
                    .setUrl(imagemUrl);

            produto.addImagem(produtoImagem);
        });
    }

    @Transactional
    public ProdutoOpiniao cadastrarOpiniao(
            Long usuarioId,
            Long produtoId,
            CadastrarProdutoOpiniaoRequestDTO requestDTO) {

        Usuario usuario = usuarioService.buscaPeloId(usuarioId);
        Produto produto = buscaPeloId(produtoId).get();

        var produtoOpiniao = new ProdutoOpiniao()
                .setUsuario(usuario)
                .setNota(requestDTO.getNota())
                .setTitulo(requestDTO.getTitulo())
                .setDescricao(requestDTO.getDescricao());

        produto.addOpiniao(produtoOpiniao);

        return produtoOpiniao;
    }

    @Transactional
    public ProdutoPergunta cadastrarPergunta(
            Long usuarioId,
            Long produtoId,
            CadastraProdutoPerguntaRequestDTO requestDTO) {

        Usuario usuario = usuarioService.buscaPeloId(usuarioId);
        Produto produto = buscaPeloId(produtoId).get();

        ProdutoPergunta produtoPergunta = new ProdutoPergunta()
                .setTitulo(requestDTO.getTitulo())
                .setUsuario(usuario);

        produto.addPergunta(produtoPergunta);

        emailService.enviarEmail(produtoPergunta);

        return produtoPergunta;
    }

    @Transactional
    public void removeTodos() {
        entityManager.createQuery("DELETE FROM " + Produto.class.getName())
                .executeUpdate();
    }

    /**
     * @todo REFATORAR
     */
    private Set<ProdutoCaracteristica> buscaCaracteristicas(
            Set<ProdutoCaracteristicaRequestDTO> caracteristicas) {

        Set<ProdutoCaracteristica> produtoCaracteristicas = new HashSet<>();

        caracteristicas.forEach(caracteristicaDTO -> {
            var caracteristica = caracteristicaService.buscaPeloId(caracteristicaDTO.getCaracteristicaId());
            var produtoCaracteristica = new ProdutoCaracteristica()
                    .setCaracteristica(caracteristica)
                    .setComplemento(caracteristicaDTO.getComplemento())
                    .setValor(caracteristicaDTO.getValor());
            produtoCaracteristicas.add(produtoCaracteristica);
        });

        return produtoCaracteristicas;
    }

    /**
     * @todo REFATORAR
     */
    private Optional<Produto> retornaPrimeiroRegistroDaLista(
            List<Produto> produtosLista,
            String atributo) {

        Assert.state(produtosLista.size() < 2, "Há mais de um produto cadastrado com o mesmo " + atributo + "!");

        if (produtosLista.size() == 0)
            return Optional.ofNullable(null);

        return Optional.ofNullable(produtosLista.get(0));
    }
}
