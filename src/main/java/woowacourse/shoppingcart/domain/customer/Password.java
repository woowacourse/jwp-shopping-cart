package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

public class Password {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$");

    private final String value;

    public Password(String value) {
        validatePasswordFormat(value);
        this.value = value;
    }

    private void validatePasswordFormat(String password) {
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new InvalidCustomerException("잘못된 비밀번호 형식입니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Password password = (Password) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
