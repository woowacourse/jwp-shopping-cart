package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.exception.customer.InvalidPasswordException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Password {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{8,20}$");

    private final String value;

    public Password(String value) {
        validate(value);
        this.value = value;
    }

    public boolean isValidPassword(String value) {
        return this.value.equals(value);
    }

    public void validate(String value) {
        Matcher matcher = PASSWORD_PATTERN.matcher(value);
        if (!matcher.find()) {
            throw new InvalidPasswordException();
        }
    }

    public String getValue() {
        return value;
    }
}
