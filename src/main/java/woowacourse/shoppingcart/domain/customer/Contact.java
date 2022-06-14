package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;
import woowacourse.auth.exception.BadRequestException;

public class Contact {

    private static final String REGULAR_EXPRESSION = "^\\d{11}$";
    private static final Pattern COMPILED_PATTERN = Pattern.compile(REGULAR_EXPRESSION);
    static final String INVALID_CONTACT_FORMAT = "핸드폰 양식의 전화번호를 입력해야합니다.";

    private final String value;

    public Contact(final String target) {
        validateContact(target);
        this.value = target;
    }

    private void validateContact(final String target) {
        if (!COMPILED_PATTERN.matcher(target).matches()) {
            throw new BadRequestException(INVALID_CONTACT_FORMAT);
        }
    }

    public String getValue() {
        return value;
    }
}
