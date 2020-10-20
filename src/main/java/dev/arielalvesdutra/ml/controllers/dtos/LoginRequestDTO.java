package dev.arielalvesdutra.ml.controllers.dtos;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

public class LoginRequestDTO {

    @NotBlank(message = "Login não pode ser vazio!")
    private String login;
    @NotBlank(message = "Senha não pode ser vazia!")
    private String senha;

    public LoginRequestDTO() {
    }

    public String getLogin() {
        return login;
    }

    public LoginRequestDTO setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getSenha() {
        return senha;
    }

    public LoginRequestDTO setSenha(String senha) {
        this.senha = senha;
        return this;
    }

    public UsernamePasswordAuthenticationToken paraAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(login, senha);
    }
}
