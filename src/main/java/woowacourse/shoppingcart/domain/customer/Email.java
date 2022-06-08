package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.exception.customer.InvalidEmailException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");

    private final String value;

    public Email(String value) {
        validate(value);
        this.value = value;
    }

    public void validate(String value) {
        Matcher matcher = EMAIL_PATTERN.matcher(value);
        if (!matcher.find()) {
            throw new InvalidEmailException();
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
