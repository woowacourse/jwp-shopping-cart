package cart.controller.authentication;

import cart.exception.AuthorizationException;

public class AuthInfo {

    private final String email;
    private final String password;

    public AuthInfo(final String email, final String password) {
        validateEmail(email);
        validatePassword(password);
        this.email = email;
        this.password = password;
    }

    private void validateEmail(final String email) {
        if (email == null || email.isBlank()) {
            throw new AuthorizationException("회원 계정이 입력되지 않았습니다.");
        }
    }

    private void validatePassword(final String password) {
        if (password == null || password.isBlank()) {
            throw new AuthorizationException("회원 비밀번호가 입력되지 않았습니다.");
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
