package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidEmailException;

public class Email {

    private static final String EMAIL_FORM = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private final String email;

    public Email(String email) {
        this.email = email;
        validate();
    }

    private void validate() {
        if(!email.matches(EMAIL_FORM)) {
            throw new InvalidEmailException();
        }
    }
}
