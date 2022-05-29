package woowacourse.auth.domain;

import java.util.regex.Pattern;
import woowacourse.auth.exception.InvalidEmailException;

public class Email {

    private static final Pattern FORM_PATTERN = Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z0-9]+[.]com");

    private final String value;

    public Email(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!FORM_PATTERN.matcher(value).matches()) {
            throw new InvalidEmailException();
        }
    }
}
