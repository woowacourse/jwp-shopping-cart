package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import java.util.regex.Pattern;

public class PlainPassword {

    private static final int MINIMUM_LENGTH = 8;
    private static final int MAXIMUM_LENGTH = 20;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])[A-Za-z0-9]*$");

    private final String plainPassword;

    public PlainPassword(final String plainPassword) {
        validateLength(plainPassword);
        validatePattern(plainPassword);
        this.plainPassword = plainPassword;
    }

    private static void validateLength(final String plainPassword) {
        if (plainPassword.length() < MINIMUM_LENGTH || plainPassword.length() > MAXIMUM_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("비밀번호는 %d자 이상 %d자 이하입니다.", MINIMUM_LENGTH, MAXIMUM_LENGTH));
        }
    }

    private static void validatePattern(final String plainPassword) {
        if (!PASSWORD_PATTERN.matcher(plainPassword).matches()) {
            throw new IllegalArgumentException("패스워드는 숫자와 영어를 포함해야합니다.");
        }
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PlainPassword that = (PlainPassword) o;
        return Objects.equals(plainPassword, that.plainPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plainPassword);
    }
}
