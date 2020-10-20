package dev.arielalvesdutra.ml.error_handlers;

import dev.arielalvesdutra.ml.controllers.dtos.ErroResponseDTO;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.Instant;

import static dev.arielalvesdutra.ml.utils.HandlerAdviceUtils.constraintViolationsToString;

// 1 HandlerAdviceUtils.java
// 2 ErroResponseDTO.java
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ConstraintViolationHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErroResponseDTO> handler(
            HttpServletRequest request,
            ConstraintViolationException exception) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroResponseDTO error = new ErroResponseDTO()
                .setErros(constraintViolationsToString(exception.getConstraintViolations()))
                .setStatus(status.value())
                .setCaminho(request.getRequestURI())
                .setInstante(Instant.now())
                .setErro(status.name())
                .setMensagem("Dados inválidos!");

        return ResponseEntity.status(status).body(error);
    }
}
