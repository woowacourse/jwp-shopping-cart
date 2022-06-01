package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import woowacourse.auth.exception.InvalidAuthException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

public class Password {

    private static final int PASSWORD_LIMIT_LENGTH = 64;

    private final String password;

    public Password(final String password) {
        validateLength(password);
        this.password = password;
    }

    private void validateLength(final String password) {
        if (password.length() != PASSWORD_LIMIT_LENGTH) {
            throw new InvalidCustomerException("비밀번호의 길이가 올바르지 않습니다.");
        }
    }

    public void matchPassword(final PasswordEncoder passwordEncoder, final PlainPassword plainPassword) {
        if (!passwordEncoder.isMatchPassword(this, plainPassword)) {
            throw new InvalidAuthException("비밀번호가 일치하지 않습니다.");
        }
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Password password1 = (Password) o;
        return Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}
