package cart.domain.member;

import java.util.Objects;

public class MemberPassword {

    private static final int PASSWORD_MAX_LENGTH = 50;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private final String value;

    public MemberPassword(final String password) {
        validatePassword(password);
        this.value = password;
    }

    private void validatePassword(final String password) {
        if (PASSWORD_MIN_LENGTH > password.length() || password.length() > PASSWORD_MAX_LENGTH) {
            throw new IllegalArgumentException(PASSWORD_MIN_LENGTH + "자 이상 " +
                    PASSWORD_MAX_LENGTH + "자 이하의 비밀번호를 입력해 주세요.");
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
        final MemberPassword memberPassword = (MemberPassword) o;
        return Objects.equals(value, memberPassword.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
