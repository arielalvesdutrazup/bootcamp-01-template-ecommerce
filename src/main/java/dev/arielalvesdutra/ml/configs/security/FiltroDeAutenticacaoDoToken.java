package dev.arielalvesdutra.ml.configs.security;

import dev.arielalvesdutra.ml.services.TokenService;
import dev.arielalvesdutra.ml.services.UsuarioService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 1 TokenService.java
// 2 UsuarioService.java
// 3 Usuario.java
// 4 if (tokenService.ehTokenValido(token))
public class FiltroDeAutenticacaoDoToken extends OncePerRequestFilter {

    private TokenService tokenService;

    private UsuarioService usuarioService;

    public FiltroDeAutenticacaoDoToken(
            TokenService tokenService,
            UsuarioService usuarioService) {

        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String token = tokenService.recuperaToken(request);

        if (tokenService.ehTokenValido(token))
            this.setUsuarioAutenticado(token);

        filterChain.doFilter(request, response);
    }

    private void setUsuarioAutenticado(String token) {
        var usuarioId = tokenService.getUsuarioId(token);
        var usuario = usuarioService.buscaPeloId(usuarioId);

        var autenticacao = new UsernamePasswordAuthenticationToken(
                usuario,
                null,
                usuario.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(autenticacao);
    }
}
