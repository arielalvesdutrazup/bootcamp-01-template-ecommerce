package dev.arielalvesdutra.ml.services;


import dev.arielalvesdutra.ml.controllers.dtos.CadastrarCaracteristicaRequestDTO;
import dev.arielalvesdutra.ml.entities.Caracteristica;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

// 1 Caracteristica.java
// 2 CadastrarCaracteristicaRequestDTO.java
// 3 if (existePeloNome(requestDTO.getNome())) {
@Service
public class CaracteristicaService {

    @PersistenceContext
    private EntityManager entityManager;

    public Caracteristica buscaPeloId(Long caracteristicaId) {
        Assert.notNull(caracteristicaId, "Id para busca da característica não pode ser nulo!");

        return entityManager.find(Caracteristica.class, caracteristicaId);
    }

    @Transactional
    public Caracteristica cadastrar(CadastrarCaracteristicaRequestDTO requestDTO) {
        if (existePeloNome(requestDTO.getNome())) {
            throw new EntityExistsException("Já existe uma característica com o mesmo nome!");
        }

        Caracteristica caracteristica = new Caracteristica()
                .setNome(requestDTO.getNome());

        entityManager.persist(caracteristica);

        return caracteristica;
    }

    /**
     * @param nome
     * @return
     */
    private boolean existePeloNome(String nome) {
        Assert.notNull(nome, "Nome para busca de características não pode ser nulo!");

        return entityManager.createQuery(
                "SELECT u.id " +
                        "FROM " + Caracteristica.class.getName() + " u " +
                        "WHERE u.nome = :nome")
                .setParameter("nome", nome)
                .getResultList().size() > 0;
    }

    @Transactional
    public void removeTodos() {
        entityManager.createQuery("DELETE FROM " + Caracteristica.class.getName())
                .executeUpdate();
    }
}
