package cart.auth;

import cart.global.exception.auth.InvalidEmailFormatException;

import java.util.regex.Pattern;

public class AuthAccount {

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    private final String email;
    private final String password;

    public AuthAccount(final String email, final String password) {
        checkEmailFormat(email);
        this.email = email;
        this.password = password;
    }

    private void checkEmailFormat(final String email) {
        if (!Pattern.compile(EMAIL_REGEX)
                    .matcher(email)
                    .matches()) {
            throw new InvalidEmailFormatException("이메일 형식이 올바르지 않습니다");
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
