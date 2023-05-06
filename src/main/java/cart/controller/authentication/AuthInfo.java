package cart.controller.authentication;

import cart.exception.AuthorizationException;

public class AuthInfo {

    private final String email;
    private final String password;

    public AuthInfo(final String email, final String password) {
        if (email == null || password == null) {
            throw new AuthorizationException("회원 정보가 입력되지 않았습니다.");
        }
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
