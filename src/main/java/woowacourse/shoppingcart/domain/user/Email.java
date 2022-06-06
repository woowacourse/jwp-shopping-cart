package woowacourse.shoppingcart.domain.user;

import woowacourse.shoppingcart.exception.InvalidEmailException;

public class Email {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private final String email;

    public Email(String email) {
        this.email = email;
        validate();
    }

    private void validate() {
        if(!email.matches(EMAIL_REGEX)) {
            throw new InvalidEmailException();
        }
    }

    public String value() {
        return email;
    }
}
