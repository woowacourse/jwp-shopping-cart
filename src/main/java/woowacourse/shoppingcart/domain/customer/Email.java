package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;

import woowacourse.shoppingcart.exception.attribute.InvalidFormException;

public class Email implements DistinctAttribute {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9-_.]+@[a-z]+[.][a-z]{2,3}");

    private final String value;

    public Email(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw InvalidFormException.fromName("이메일");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public String getDistinctive() {
        return getValue();
    }
}
