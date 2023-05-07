package cart.validator;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class CostumeDefaultValidator {

    final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public <T> void validate(T target) {
        final Set<ConstraintViolation<T>> violations = validator.validate(target);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
