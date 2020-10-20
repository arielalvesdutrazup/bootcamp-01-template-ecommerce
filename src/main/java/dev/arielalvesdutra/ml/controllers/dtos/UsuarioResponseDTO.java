package dev.arielalvesdutra.ml.controllers.dtos;

import dev.arielalvesdutra.ml.entities.Usuario;

import java.time.OffsetDateTime;

public class UsuarioResponseDTO {

    private Long id;
    private String login;
    private OffsetDateTime cadastradoEm;

    public UsuarioResponseDTO() {}

    public UsuarioResponseDTO(Usuario usuario) {
        setId(usuario.getId());
        setLogin(usuario.getLogin());
        setCadastradoEm(usuario.getCadastradoEm());
    }

    public Long getId() {
        return id;
    }

    public UsuarioResponseDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public UsuarioResponseDTO setLogin(String login) {
        this.login = login;
        return this;
    }

    public OffsetDateTime getCadastradoEm() {
        return cadastradoEm;
    }

    public UsuarioResponseDTO setCadastradoEm(OffsetDateTime cadastradoEm) {
        this.cadastradoEm = cadastradoEm;
        return this;
    }
}
