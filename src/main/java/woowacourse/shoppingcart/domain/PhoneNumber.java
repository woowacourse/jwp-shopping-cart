package woowacourse.shoppingcart.domain;

import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.InvalidPhoneNumberException;

public class PhoneNumber {

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^010-[0-9]{4}-[0-9]{4}");
    private final String value;

    public PhoneNumber(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!PHONE_NUMBER_PATTERN.matcher(value).matches()) {
            throw new InvalidPhoneNumberException();
        }
    }
}
