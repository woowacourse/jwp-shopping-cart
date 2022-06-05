package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;
import woowacourse.common.exception.InputFormatException;
import woowacourse.common.exception.dto.ErrorResponse;

public class Email {
    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9+-\\\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");

    private final String value;

    public Email(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!PATTERN.matcher(value).matches()) {
            throw new InputFormatException("이메일 규약이 맞지 않습니다", ErrorResponse.INVALID_EMAIL);
        }
    }

    public String getValue() {
        return value;
    }
}
