package woowacourse.shoppingcart.domain.customer.values;

import java.util.Objects;
import java.util.regex.Pattern;

public class Username {

    private static final int MINIMUM_LENGTH = 3;
    private static final int MAXIMUM_LENGTH = 15;
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[0-9a-zA-Z]*$");

    private final String username;

    public Username(final String username) {
        validateLength(username);
        validatePattern(username);
        this.username = username;
    }

    private void validateLength(final String username) {
        if (username.length() < MINIMUM_LENGTH || username.length() > MAXIMUM_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("유저 이름은 %d자 이상 %d자 이하입니다.", MINIMUM_LENGTH, MAXIMUM_LENGTH));
        }
    }

    private void validatePattern(final String username) {
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new IllegalArgumentException("유저 이름은 영어와 숫자만 가능합니다.");
        }
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Username username1 = (Username) o;
        return Objects.equals(username, username1.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
