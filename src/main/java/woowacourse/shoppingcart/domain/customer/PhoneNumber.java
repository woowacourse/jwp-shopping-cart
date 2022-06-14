package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.InvalidFormException;

public class PhoneNumber {

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^010-[0-9]{4}-[0-9]{4}");
    private final String value;

    public PhoneNumber(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!PHONE_NUMBER_PATTERN.matcher(value).matches()) {
            throw InvalidFormException.fromName("휴대폰 번호");
        }
    }

    public String getValue() {
        return value;
    }
}
