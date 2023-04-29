package cart.domain;

import java.util.Objects;

public class Member {
    private final String email;
    private final String password;

    public Member(String email, String password) {
        validate(email, password);
        this.email = email;
        this.password = password;
    }

    private void validate(String email, String password) {
        validateEmail(email);
        validatePassword(password);
    }

    private void validateEmail(String email) {
        if (Objects.isNull(email) || email.isBlank()) {
            throw new IllegalArgumentException("이메일은 빈 값이 될 수 없습니다.");
        }
        if (email.contains(":")) {
            throw new IllegalArgumentException("이메일에 \":\"가 포함될 수 없습니다.");
        }
    }

    private void validatePassword(String password) {
        if (Objects.isNull(password) || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 빈 값이 될 수 없습니다.");
        }
        if (password.contains(":")) {
            throw new IllegalArgumentException("비밀번호에 \":\"가 포함될 수 없습니다.");
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
