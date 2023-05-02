package cart.domain.member;

import java.util.Objects;

public class Username {

    private static final int USERNAME_MAX_LENGTH = 100;
    private final String value;

    public Username(final String username) {
        validateUsername(username);
        this.value = username;
    }

    private void validateUsername(final String username) {
        if (username.length() > USERNAME_MAX_LENGTH) {
            throw new IllegalArgumentException(USERNAME_MAX_LENGTH + "자 이하의 사용자명을 입력해 주세요.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Username username = (Username) o;
        return Objects.equals(value, username.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
