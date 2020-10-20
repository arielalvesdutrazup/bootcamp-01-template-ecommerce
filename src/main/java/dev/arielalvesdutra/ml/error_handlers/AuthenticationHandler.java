package dev.arielalvesdutra.ml.error_handlers;

import dev.arielalvesdutra.ml.controllers.dtos.ErroResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class AuthenticationHandler {

    @ExceptionHandler(AuthenticationException .class)
    public ResponseEntity<ErroResponseDTO> handler(
            HttpServletRequest request,
            AuthenticationException exception) {

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErroResponseDTO error = new ErroResponseDTO()
                .setStatus(status.value())
                .setCaminho(request.getRequestURI())
                .setInstante(Instant.now())
                .setErro(status.name())
                .setMensagem("Dados de autenticação inválidos!");

        return ResponseEntity.status(status).body(error);
    }
}
