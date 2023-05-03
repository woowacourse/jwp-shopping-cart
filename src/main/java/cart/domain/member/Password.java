package cart.domain.member;

import java.util.Objects;

public class Password {
    private static final int MIN_PASSWORD_LENGTH = 4;
    private static final int MAX_PASSWORD_LENGTH = 20;
    private final String password;

    public Password(String password) {
        validate(password);
        this.password = password;
    }

    private void validate(String password) {
        validateBlank(password);
        validateLength(password);
        validateColon(password);
    }

    private void validateBlank(String password) {
        if (Objects.isNull(password) || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 빈 값이 될 수 없습니다.");
        }
    }

    private void validateLength(String password) {
        int length = password.length();
        if (length < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("비밀번호의 길이는 " + MIN_PASSWORD_LENGTH + "자리 이상이어야 합니다.");
        }
        if (length > MAX_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("비밀번호의 길이는 " + MAX_PASSWORD_LENGTH + "자리 이하여야 합니다.");
        }
    }

    private void validateColon(String password) {
        if (password.contains(":")) {
            throw new IllegalArgumentException("비밀번호에 \":\"가 포함될 수 없습니다.");
        }
    }

    public String getPassword() {
        return password;
    }
}
