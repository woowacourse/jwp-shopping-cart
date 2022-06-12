package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.exception.InvalidCustomerException;

public class Email {

    private static final int UPPER_BOUND_LENGTH = 64;

    private final String email;

    public Email(final String email) {
        checkLength(email);
        this.email = email;
    }

    private void checkLength(final String email) {
        if (email.length() > UPPER_BOUND_LENGTH) {
            throw new InvalidCustomerException("[ERROR] 이메일의 길이는 64자를 넘을 수 없습니다.");
        }
    }

    public String get() {
        return email;
    }
}
