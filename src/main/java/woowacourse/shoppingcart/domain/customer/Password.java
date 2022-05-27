package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

public class Password {

    private static final int MINIMUM_LENGTH = 8;
    private static final int MAXIMUM_LENGTH = 20;

    private final String password;

    public Password(final String password) {
        validateLength(password);
        this.password = password;
    }

    private void validateLength(final String password) {
        if (password.length() < MINIMUM_LENGTH || password.length() > MAXIMUM_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("비밀번호는 %d자 이상 %d자 이하입니다.", MINIMUM_LENGTH, MAXIMUM_LENGTH));
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
