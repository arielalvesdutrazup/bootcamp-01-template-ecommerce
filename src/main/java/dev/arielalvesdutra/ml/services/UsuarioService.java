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

// 1 Usuario.java
// 2 CadastrarUsuarioRequestDTO.java
// 3 if (existePeloLogin(requestDTO.getLogin())) {
@Service
public class UsuarioService {

    @PersistenceContext
    private EntityManager entityManager;

    public Usuario buscarPeloId(Long id) {
        Assert.notNull(id, "Id para busca do usuário não pode ser nulo!");

        return entityManager.find(Usuario.class, id);
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
        entityManager.createQuery("DELETE FROM " + Usuario.class.getName());
    }
}
