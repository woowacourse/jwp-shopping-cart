package woowacourse.auth.domain;

import woowacourse.shoppingcart.exception.InvalidEmailException;

public class UserEmail {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

    private final String email;

    public UserEmail(String email) {
        validate(email);
        this.email = email;
    }

    private void validate(String email) {
        if (email == null || email.isBlank() || !email.matches(EMAIL_REGEX)) {
            throw new InvalidEmailException();
        }
    }

    public String getEmail() {
        return email;
    }
}
