package cart.validator;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public final class DefaultValidator{

	static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	public static<T> void validate(T target) {
		final Set<ConstraintViolation<T>> violations = validator.validate(target);
		if(!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
	}
}
