package cart.domain.member;

import java.util.Objects;

public class MemberPassword {

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 50;

    private final String value;

    public MemberPassword(final String password) {
        validatePassword(password);
        this.value = password;
    }

    private static void validatePassword(final String password) {
        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(MIN_LENGTH + "-" + MAX_LENGTH + "자 사이의 비밀번호를 입력해 주세요.");
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

    public void encrypt() {
        System.out.println("Password Encrypted");
    }
}
