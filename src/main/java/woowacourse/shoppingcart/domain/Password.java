package woowacourse.shoppingcart.domain;

import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.IllegalPasswordException;

public class Password {

    private static final String REGEX = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,20}";

    private final String value;

    public Password(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!Pattern.matches(REGEX, value)) {
            throw new IllegalPasswordException();
        }
    }
}
