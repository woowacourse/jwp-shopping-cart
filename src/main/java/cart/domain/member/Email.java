package cart.domain.member;

import cart.exception.EmailCreateFailException;

public class Email {

    private static final String EMAIL_REGEX = "[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

    private final String email;

    public Email(final String email) {
        validate(email);
        this.email = email;
    }

    private void validate(final String email) {
        if (email.isBlank()) {
            throw new EmailCreateFailException();
        }

        if (!email.matches(EMAIL_REGEX)) {
            throw new EmailCreateFailException();
        }
    }

    public String getEmail() {
        return email;
    }
}
