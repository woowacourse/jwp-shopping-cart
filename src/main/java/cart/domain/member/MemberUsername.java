package cart.domain.member;

import java.util.Objects;

public class MemberUsername {

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 100;

    private final String value;

    public MemberUsername(final String username) {
        validateUsername(username);
        this.value = username;
    }

    public String getValue() {
        return value;
    }

    private void validateUsername(final String username) {
        if (username.length() < MIN_LENGTH || username.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(MIN_LENGTH + "-" + MAX_LENGTH + "자 사이의 사용자명을 입력해 주세요.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MemberUsername memberUsername = (MemberUsername) o;
        return Objects.equals(value, memberUsername.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
