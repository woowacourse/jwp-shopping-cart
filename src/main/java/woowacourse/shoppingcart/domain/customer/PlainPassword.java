package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.IllegalFormException;

public class PlainPassword {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_-])[a-zA-Z0-9!@#$%^&*_-]{8,16}$");

    private final String value;

    public PlainPassword(final String value) {
        validatePattern(value);
        this.value = value;
    }

    private void validatePattern(final String value) {
        if (!PASSWORD_PATTERN.matcher(value).matches()) {
            throw new IllegalFormException("비밀번호");
        }
    }

    public EncryptPassword toEncryptPassword(final PasswordEncryptor passwordEncryptor) {
        return new EncryptPassword(passwordEncryptor.encode(value));
    }

    public String getValue() {
        return value;
    }
}
