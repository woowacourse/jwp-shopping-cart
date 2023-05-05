package cart.domain.member;

import java.util.Objects;

public class MemberPassword {

    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 16;

    private final String password;

    public MemberPassword(String password) {
        this.password = password;
        validate(this.password);
    }

    private void validate(String password) {
        if(password.isBlank()) {
            throw new IllegalArgumentException("회원 비밀번호는 공백일 수 없습니다.");
        }

        if(password.length()> MAX_LENGTH || password.length() < MIN_LENGTH) {
            throw new IllegalArgumentException("회원 비밀번호는 4자 이상 16자 이하여야 합니다.");
        }
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberPassword that = (MemberPassword) o;
        return Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}
