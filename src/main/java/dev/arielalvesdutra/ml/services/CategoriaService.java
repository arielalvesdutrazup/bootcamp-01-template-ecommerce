package dev.arielalvesdutra.ml.services;

import dev.arielalvesdutra.ml.controllers.dtos.CadastrarCategoriaRequestDTO;
import dev.arielalvesdutra.ml.entities.Categoria;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

// 1 Categoria.java
// 2 CadsatrarCategoriaRequestDTO.java
// 3 if (existePeloNome(requestDTO.getNome())) {
// 4 if (requestDTO.getCategoriaMaeId() != null) {
@Service
public class CategoriaService {

    @PersistenceContext
    private EntityManager entityManager;

    public Categoria buscarPeloId(Long id) {
        Assert.notNull(id, "Id para busca da categoria não pode ser nulo!");

        return entityManager.find(Categoria.class, id);
    }

    @Transactional
    public Categoria cadastrar(CadastrarCategoriaRequestDTO requestDTO) {
        if (existePeloNome(requestDTO.getNome())) {
            throw new EntityExistsException("Já existe uma categoria com o mesmo nome!");
        }

        Categoria categoria = new Categoria()
                .setNome(requestDTO.getNome());

        if (requestDTO.getCategoriaMaeId() != null) {
            categoria.setCategoriaMae(buscarPeloId(requestDTO.getCategoriaMaeId()));
        }

        entityManager.persist(categoria);

        return categoria;
    }

    public boolean existePeloNome(String nome) {
        Assert.notNull(nome, "Nome para busca de categorias não pode ser nulo!");

        return entityManager.createQuery(
                "SELECT o.id " +
                        "FROM " + Categoria.class.getName() + " o " +
                        "WHERE o.nome = :nome")
                .setParameter("nome", nome)
                .getResultList().size() > 0;
    }

    @Transactional
    public void removeTodos() {
        entityManager.createQuery("DELETE FROM " + Categoria.class.getName());
    }
}
