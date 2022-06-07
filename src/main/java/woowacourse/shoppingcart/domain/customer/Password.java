package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.InvalidFormException;
import woowacourse.shoppingcart.exception.InvalidLengthException;

public class Password {

    private static final int MIN_THRESHOLD = 8;
    private static final int MAX_THRESHOLD = 16;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()]).+$");

    private final String value;

    public Password(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        validateLength(value);
        validateForm(value);
    }

    private void validateLength(String value) {
        if (!isValidLength(value)) {
            throw InvalidLengthException.fromName("패스워드");
        }
    }

    private boolean isValidLength(String value) {
        return MIN_THRESHOLD <= value.length() && value.length() <= MAX_THRESHOLD;
    }

    private void validateForm(String value) {
        if (!PASSWORD_PATTERN.matcher(value).matches()) {
            throw InvalidFormException.fromName("패스워드");
        }
    }

    public boolean isMatch(String password) {
        return this.value.equals(password);
    }

    public String getValue() {
        return value;
    }
}
