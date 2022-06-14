package woowacourse.shoppingcart.customer.domain;

import java.util.regex.Pattern;

import woowacourse.shoppingcart.customer.support.exception.CustomerException;
import woowacourse.shoppingcart.customer.support.exception.CustomerExceptionCode;

public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    private final String value;

    public Email(final String value) {
        validateEmail(value);
        this.value = value;
    }

    private void validateEmail(final String value) {
        if (isEmailOutOfForm(value)) {
            throw new CustomerException(CustomerExceptionCode.INVALID_FORMAT_EMAIL);
        }
    }

    private boolean isEmailOutOfForm(final String value) {
        return !EMAIL_PATTERN.matcher(value).matches();
    }

    public String value() {
        return value;
    }
}
