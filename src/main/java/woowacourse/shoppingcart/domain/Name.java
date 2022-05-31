package woowacourse.shoppingcart.domain;

import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.InvalidNameException;

public class Name {

    private static final int MIN_NAME_LENGTH = 5;
    private static final int MAX_NAME_LENGTH = 20;
    private static final Pattern NAME_FORM_PATTERN = Pattern.compile("[^a-z0-9_-]+");

    private final String value;

    public Name(String value) {
        validateLength(value);
        validateForm(value);
        this.value = value;
    }

    private void validateLength(final String value) {
        if (!isLengthInRange(value)) {
            throw new InvalidNameException();
        }
    }

    private boolean isLengthInRange(String value) {
        return value.length() >= MIN_NAME_LENGTH && value.length() <= MAX_NAME_LENGTH;
    }

    private void validateForm(String value) {
        if (NAME_FORM_PATTERN.matcher(value).find()) {
            throw new InvalidNameException();
        }
    }

    public String getValue() {
        return value;
    }
}
