package cart.entity;

import cart.exception.EmailFormatNotValidException;

public class Member {

    private static final String VALID_EMAIL_FORMAT = "\\w+@\\w+\\.\\w+";

    private final Long id;
    private final String email;
    private final String password;

    public Member(final Long id, final String email, final String password) {
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

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
