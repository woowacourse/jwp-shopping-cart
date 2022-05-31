package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;

public class Contact {

    private static final String REGULAR_EXPRESSION = "^\\d{11}$";
    private static final Pattern COMPILED_PATTERN = Pattern.compile(REGULAR_EXPRESSION);
    static final String INVALID_CONTACT_FORMAT = "올바르지 않은 전화번호입니다.";

    private final String value;

    public Contact(final String target) {
        validateContact(target);
        this.value = target;
    }

    private void validateContact(final String target) {
        if (!COMPILED_PATTERN.matcher(target).matches()) {
            throw new IllegalArgumentException(INVALID_CONTACT_FORMAT);
        }
    }

    public String getValue() {
        return value;
    }
}
