package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;

import woowacourse.auth.domain.PasswordMatcher;
import woowacourse.shoppingcart.exception.attribute.InvalidFormException;
import woowacourse.shoppingcart.exception.attribute.InvalidLengthException;

public class Password {

    private static final int MIN_THRESHOLD = 8;
    private static final int MAX_THRESHOLD = 16;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()]).+$");

    private final String value;

    private Password(String value) {
        this.value = value;
    }

    public static Password fromInput(String value) {
        validate(value);
        return new Password(value);
    }

    public static Password fromEncoded(String value) {
        return new Password(value);
    }

    private static void validate(String value) {
        validateLength(value);
        validateForm(value);
    }

    private static void validateLength(String value) {
        if (!isValidLength(value)) {
            throw InvalidLengthException.fromName("패스워드");
        }
    }

    private static boolean isValidLength(String value) {
        return MIN_THRESHOLD <= value.length() && value.length() <= MAX_THRESHOLD;
    }

    private static void validateForm(String value) {
        if (!PASSWORD_PATTERN.matcher(value).matches()) {
            throw InvalidFormException.fromName("패스워드");
        }
    }

    public Password encrypt(PasswordEncryptor encryptor) {
        return new Password(encryptor.encrypt(value));
    }

    public boolean isMatch(String password, PasswordMatcher matcher) {
        return matcher.isMatch(password, value);
    }

    public String getValue() {
        return value;
    }
}
