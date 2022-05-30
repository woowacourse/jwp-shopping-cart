package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import java.util.regex.Pattern;
import woowacourse.auth.exception.InvalidAuthException;

public class Password {

    private static final int MINIMUM_LENGTH = 8;
    private static final int MAXIMUM_LENGTH = 20;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])[A-Za-z0-9]*$");

    private final String password;

    private Password(final String password) {
        this.password = password;
    }

    public static Password purePassword(final String password) {
        validateLength(password);
        validatePattern(password);
        return new Password(password);
    }

    private static void validateLength(final String password) {
        if (password.length() < MINIMUM_LENGTH || password.length() > MAXIMUM_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("비밀번호는 %d자 이상 %d자 이하입니다.", MINIMUM_LENGTH, MAXIMUM_LENGTH));
        }
    }

    private static void validatePattern(final String password) {
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException("패스워드는 숫자와 영어를 포함해야합니다.");
        }
    }

    public static Password encodedPassword(final String password) {
        return new Password(password);
    }

    public Password encodePassword(final PasswordEncoder passwordEncoder) {
        return new Password(passwordEncoder.encode(password));
    }

    public void matchPassword(final PasswordEncoder passwordEncoder, final String password) {
        if (!passwordEncoder.isMatchPassword(this.password, password)) {
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
