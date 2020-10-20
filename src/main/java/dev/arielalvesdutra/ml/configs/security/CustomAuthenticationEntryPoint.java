package dev.arielalvesdutra.ml.configs.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.arielalvesdutra.ml.controllers.dtos.ErroResponseDTO;
import dev.arielalvesdutra.ml.error_handlers.AuthenticationHandler;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Classe responsável pelo retorno para requisições não autenticados (UNAUTHORIZED).
 *
 * Exceto tratamentos específicos como em {@link AuthenticationHandler}.
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        var objectMapper = new ObjectMapper();
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        var responseDTO = new ErroResponseDTO()
                .setStatus(status.value())
                .setCaminho(request.getRequestURI())
                .setErro(status.name())
                .setMensagem("Usuário não autenticado!");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status.value());
        response.getWriter().write(objectMapper.writeValueAsString(responseDTO));
    }
}
