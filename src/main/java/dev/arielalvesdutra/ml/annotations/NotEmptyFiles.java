package dev.arielalvesdutra.ml.annotations;

import dev.arielalvesdutra.ml.annotations.validators.NotEmptyFilesValidator;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * Anotação para verificar se um parâmetro com uma lista de {@link MultipartFile}
 * possui algum arquivo vazio.
 */
@Documented
@Constraint(validatedBy = NotEmptyFilesValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD })
public @interface NotEmptyFiles {
    String message() default "Não pode conter items vazios na lista de arquivos!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
