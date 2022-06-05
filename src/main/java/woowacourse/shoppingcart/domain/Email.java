package woowacourse.shoppingcart.domain;

import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.IllegalEmailException;

public class Email {

    private static final String REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    private final String value;

    public Email(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!Pattern.matches(REGEX, value)) {
            throw new IllegalEmailException();
        }
    }
}
