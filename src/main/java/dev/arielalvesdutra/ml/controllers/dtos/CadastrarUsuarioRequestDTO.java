package dev.arielalvesdutra.ml.controllers.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CadastrarUsuarioRequestDTO {

    @NotBlank(message = "Login não pode ser vazio!")
    @Email(message = "Login deve ter o formato de um e-mail válido!")
    private String login;
    @NotBlank(message = "Senha não pode ser vazia!")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres!")
    private String senha;

    public CadastrarUsuarioRequestDTO() {
    }

    public String getLogin() {
        return login;
    }

    public CadastrarUsuarioRequestDTO setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getSenha() {
        return senha;
    }

    public CadastrarUsuarioRequestDTO setSenha(String senha) {
        this.senha = senha;
        return this;
    }
}
