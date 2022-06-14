package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;
import woowacourse.auth.exception.BadRequestException;

public class Email {

    private static final String REGULAR_EXPRESSION = "[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.([a-zA-Z])+";
    private static final Pattern COMPILED_PATTERN = Pattern.compile(REGULAR_EXPRESSION);
    static final String INVALID_EMAIL_FORMAT = "입력하신 이메일 주소가 올바르지 않습니다.";

    private final String value;

    public Email(final String value) {
        validateEmail(value);
        this.value = value;
    }

    private void validateEmail(final String target) {
        if (!COMPILED_PATTERN.matcher(target).matches()) {
            throw new BadRequestException(INVALID_EMAIL_FORMAT);
        }
    }

    public String getValue() {
        return value;
    }
}
