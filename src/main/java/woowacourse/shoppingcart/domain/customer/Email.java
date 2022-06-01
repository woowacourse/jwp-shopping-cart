package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9._-]+@[a-z]+[.]+[a-z]{2,3}$");

    private final String value;

    public Email(String value) {
        validateEmail(value);
        this.value = value;
    }

    private void validateEmail(String value) {
        checkNull(value);
        checkFormat(value);
    }

    private void checkNull(String value) {
        if (value == null) {
            throw new IllegalArgumentException("email은 필수 입력 사항입니다.");
        }
    }

    private void checkFormat(String value) {
        Matcher matcher = EMAIL_PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("email 형식이 올바르지 않습니다. (형식: example@email.com)");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Email email = (Email)o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
