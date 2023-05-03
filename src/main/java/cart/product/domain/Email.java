package cart.product.domain;

import java.util.regex.Pattern;

public class Email {

    private static final String VALID_EMAIL_REGEX = "^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*$";
    private static final String FORMAT_ERROR_MESSAGE = "Email 형식에 맞게 입력해주세요.";

    private final String value;

    public Email(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        validateFormat(value);
        validateBlank(value);
    }

    private void validateFormat(final String value) {
        if (!Pattern.matches(VALID_EMAIL_REGEX, value)) {
            throw new IllegalArgumentException(FORMAT_ERROR_MESSAGE);
        }
    }

    private void validateBlank(final String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException(FORMAT_ERROR_MESSAGE);
        }
    }

    public String getValue() {
        return value;
    }
}
