package dev.arielalvesdutra.ml.annotations.validators;

import dev.arielalvesdutra.ml.annotations.NotEmptyFiles;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

// 1 if (value == null || value.isEmpty())
// 2 for (MultipartFile file : value) {
// 3 if (file == null || file.isEmpty())
public class NotEmptyFilesValidator implements ConstraintValidator<NotEmptyFiles, List<MultipartFile>> {

    @Override
    public void initialize(NotEmptyFiles constraintAnnotation) { }

    @Override
    public boolean isValid(List<MultipartFile> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty())
            return false;

        for (MultipartFile file : value) {
            if (file == null || file.isEmpty())
                return false;
        }

        return true;
    }
}
