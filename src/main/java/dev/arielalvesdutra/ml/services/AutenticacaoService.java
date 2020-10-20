package dev.arielalvesdutra.ml.services;

import dev.arielalvesdutra.ml.controllers.dtos.LoginRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

// 1 LoginRequestDTO.java
// 2 TokenService.java
@Service
public class AutenticacaoService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    /**
     * Autentica usuário e retorna o token do mesmo.
     *
     * @param requestDTO DTO com login e senha.
     * @return token do usuário
     */
    public String autentica(LoginRequestDTO requestDTO) {

        UsernamePasswordAuthenticationToken upat = requestDTO.paraAuthenticationToken();
        Authentication authentication = authenticationManager.authenticate(upat);

        return tokenService.geraToken(authentication);
    }
}
