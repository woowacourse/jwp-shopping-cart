package cart.domain.user;

import cart.exception.GlobalException;

public class Password {
    private static final int MAX_LENGTH = 20;

    private final String password;

    private Password(String password) {
        validate(password);
        this.password = password;
    }

    private void validate(String password) {
        if (password.isBlank()) {
            throw new GlobalException("비밀번호는 1글자 이상이어야 합니다.");
        }

        if (password.length() > MAX_LENGTH) {
            throw new GlobalException("이메일은 20글자 이하여야 합니다.");
        }
    }

    public static Password from(String password) {
        return new Password(password);
    }

    public String getPassword() {
        return password;
    }
}
