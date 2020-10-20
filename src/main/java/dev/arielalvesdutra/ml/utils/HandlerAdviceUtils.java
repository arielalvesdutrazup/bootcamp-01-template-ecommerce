package dev.arielalvesdutra.ml.utils;

import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


// 1 forEach(error ->
// 2 constraintViolations.forEach(violation -> {
/**
 * Classe auxiliar para Handlers Adivice.
 */
public class HandlerAdviceUtils {

    /**
     * @todo rafatorar
     *
     * Converte os fieldErrors de um BindingResult para uma lista de String
     * com as mensagens default de erro.
     *
     * @param bindingResult
     * @return
     */
    public static List<String> fieldErrorsToStringList(BindingResult bindingResult) {
        List<String> errorList =  new ArrayList<>();
        bindingResult.getFieldErrors().forEach(error -> {
            errorList.add(error.getDefaultMessage());
        });

        return errorList;
    }

    /**
     * @todo rafatorar
     * @param constraintViolations
     * @return
     */
    public static List<String> constraintViolationsToString(Set<ConstraintViolation<?>> constraintViolations) {
        List<String> errorList =  new ArrayList<>();
        constraintViolations.forEach(violation -> {
            errorList.add(violation.getMessage());
        });

        return errorList;
    }
}
