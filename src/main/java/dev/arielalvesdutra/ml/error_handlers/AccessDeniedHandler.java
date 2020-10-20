package dev.arielalvesdutra.ml.error_handlers;

import dev.arielalvesdutra.ml.controllers.dtos.ErroResponseDTO;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class AccessDeniedHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErroResponseDTO> handler(
            HttpServletRequest request,
            AccessDeniedException exception) {

        HttpStatus status = HttpStatus.FORBIDDEN;
        ErroResponseDTO error = new ErroResponseDTO()
                .setStatus(status.value())
                .setCaminho(request.getRequestURI())
                .setInstante(Instant.now())
                .setErro(status.name())
                .setMensagem(exception.getMessage());

        return ResponseEntity.status(status).body(error);
    }
}
