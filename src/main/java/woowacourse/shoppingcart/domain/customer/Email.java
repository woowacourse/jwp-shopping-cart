package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.InputFormatException;
import woowacourse.exception.dto.ErrorResponse;

public class Email {

    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    private final String email;

    private Email(String email) {
        this.email = email;
    }

    public static Email of(String email) {
        validateEmail(email);
        return new Email(email);
    }

    private static void validateEmail(String email) {
        if (!PATTERN.matcher(email).matches()) {
            throw new InputFormatException("이메일 규약이 맞지 않습니다", ErrorResponse.INVALID_EMAIL);
        }
    }

    public String getValue() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Email)) {
            return false;
        }
        Email email1 = (Email) o;
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
