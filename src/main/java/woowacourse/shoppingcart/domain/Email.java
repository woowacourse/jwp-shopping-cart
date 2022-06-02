package woowacourse.shoppingcart.domain;

import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.InvalidEmailFormatException;

public class Email {

    private static final String EMAIL_FORMAT_REGEX =
            "^(?=.{1,20}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private final String value;

    public Email(final String value) {
        validateFormat(value);
        this.value = value;
    }

    private void validateFormat(final String value) {
        if (!Pattern.matches(EMAIL_FORMAT_REGEX, value)) {
            throw new InvalidEmailFormatException();
        }
    }

    public String getValue() {
        return value;
    }
}
