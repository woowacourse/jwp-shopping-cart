package cart.entity;

import cart.entity.vo.Email;

public class User {

    private final int MIN_PASSWORD_LENGTH = 5;

    private final Email email;
    private final String password;

    public User(final String email, final String password) {
        validatePassword(password);
        this.email = new Email(email);
        this.password = password;
    }

    private void validatePassword(final String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("password가 5글자 미만입니다.");
        }
    }

    public Email getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
