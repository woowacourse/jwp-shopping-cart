package cart.entity;

import cart.exception.EmailFormatNotValidException;

public class User {

    private static final String VALID_EMAIL_FORMAT = "\\w+@\\w+\\.\\w+";

    private final Long id;
    private final String email;
    private final String password;

    public User(final Long id, final String email, final String password) {
        validateEmailFormat(email);
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public void validateEmailFormat(final String email) {
        if (!email.matches(VALID_EMAIL_FORMAT)) {
            throw new EmailFormatNotValidException();
        }
    }
}
