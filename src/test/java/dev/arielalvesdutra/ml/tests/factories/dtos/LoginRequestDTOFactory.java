package dev.arielalvesdutra.ml.tests.factories.dtos;

import dev.arielalvesdutra.ml.controllers.dtos.LoginRequestDTO;

import static dev.arielalvesdutra.ml.tests.factories.dtos.CadastrarUsuarioRequestDTOFactory.paraPersistir;

public class LoginRequestDTOFactory {

    public static LoginRequestDTO loginValido() {
        var usuario = paraPersistir();
        return new LoginRequestDTO()
                .setLogin(usuario.getLogin())
                .setSenha(usuario.getSenha());
    }
}
