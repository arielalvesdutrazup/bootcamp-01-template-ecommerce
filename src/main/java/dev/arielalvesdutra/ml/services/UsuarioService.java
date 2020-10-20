package dev.arielalvesdutra.ml.services;

import dev.arielalvesdutra.ml.controllers.dtos.CadastrarUsuarioRequestDTO;
import dev.arielalvesdutra.ml.entities.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

//// Serviços

//// DTOS
// 1 CadastrarUsuarioRequestDTO.java

//// Entidades
// 2 Usuario.java

//// Condicionais
// 3 if (usurioLista.size() == 0)
// 4 if (existePeloLogin(requestDTO.getLogin())) {

//// Loops

@Service
public class UsuarioService {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<Usuario> buscaPeloLogin(String login) {
        Assert.notNull(login, "Login para busca do usuário não pode ser nulo!");

        List<Usuario> resultado = entityManager.createQuery(
                "SELECT u " +
                        "FROM " + Usuario.class.getName() + " u " +
                        "WHERE u.login = :login", Usuario.class)
                .setParameter("login", login)
                .getResultList();

        return retornaPrimeiroRegistroDaLista(resultado, "login");
    }

    /**
     * @todo Documentar!!!
     * @param id
     * @return
     */
    public Usuario buscaPeloId(Long id) {
        Assert.notNull(id, "Id para busca do usuário não pode ser nulo!");

        List<Usuario> resultado = entityManager.createQuery(
                "SELECT o " +
                       "FROM " + Usuario.class.getName() + " o " +
                       "LEFT JOIN FETCH o.perfis " +
                       "WHERE o.id = :id", Usuario.class)
                .setParameter("id", id)
                .getResultList();

        return retornaPrimeiroRegistroDaLista(resultado, "id").get();
    }

    @Transactional
    public Usuario cadastrar(CadastrarUsuarioRequestDTO requestDTO) {
        if (existePeloLogin(requestDTO.getLogin())) {
            throw new EntityExistsException("Já existe um usuário com o mesmo login!");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Usuario usuarioParaCadastrar = new Usuario()
                .setLogin(requestDTO.getLogin())
                .setSenha(passwordEncoder.encode(requestDTO.getSenha()));

        entityManager.persist(usuarioParaCadastrar);

        return usuarioParaCadastrar;
    }

    /**
     * Retorna verdadeiro se existir um usuário cadastrado com o login
     * fornecido por parâmetro.
     *
     * @param login
     * @return
     */
    public boolean existePeloLogin(String login) {
        Assert.notNull(login, "Login para busca de usuários não pode ser nulo!");

        return entityManager.createQuery(
                "SELECT u.id " +
                        "FROM " + Usuario.class.getName() + " u " +
                        "WHERE u.login = :login")
                .setParameter("login", login)
                .getResultList().size() > 0;
    }

    @Transactional
    public void removeTodos() {
        entityManager.createQuery("DELETE FROM " + Usuario.class.getName())
                .executeUpdate();
    }

    /**
     * @todo refatorar!!!
     * @todo documentar!!!!
     * @param usurioLista
     * @param criterioDeBusca
     * @return
     */
    private Optional<Usuario> retornaPrimeiroRegistroDaLista(
            List<Usuario> usurioLista,
            String criterioDeBusca) {

        Assert.state(usurioLista.size() < 2, "Há mais de um usuário cadastrado com o mesmo " +  criterioDeBusca + "!");

        if (usurioLista.size() == 0)
            return Optional.ofNullable(null);

        return Optional.ofNullable(usurioLista.get(0));
    }
}
