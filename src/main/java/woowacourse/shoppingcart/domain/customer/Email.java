package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.exception.EmailValidationException;
import woowacourse.utils.StringValidator;

public class Email {

    private static final int EMAIL_MIN_LENGTH = 8;
    private static final int EMAIL_MAX_LENGTH = 50;

    private final String value;

    private Email() {
        this.value = null;
    }

    public Email(String value) {
        validateEmail(value);
        this.value = value;
    }

    public static Email empty() {
        return new Email();
    }

    private static void validateEmail(final String email) {
        StringValidator.validateLength(EMAIL_MIN_LENGTH, EMAIL_MAX_LENGTH, email,
                new EmailValidationException("이메일은 8자 이상 50자 이하여야합니다."));
    }

    public String getValue() {
        return value;
    }
}
