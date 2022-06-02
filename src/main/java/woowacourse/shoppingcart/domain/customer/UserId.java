package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.exception.dataempty.CustomerDataEmptyException;
import woowacourse.shoppingcart.exception.dataformat.CustomerDataFormatException;

import java.util.regex.Pattern;

public class UserId {

    private final static Pattern EMAIL_FORMAT = Pattern.compile("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");

    private final String value;

    public UserId(final String value) {
        validateUserId(value);
        this.value = value;
    }

    private void validateUserId(final String value) {
        if (isEmpty(value)) {
            throw new CustomerDataEmptyException("아이디를 입력해주세요.");
        }
        if (isNotValidFormat(value)) {
            throw new CustomerDataFormatException("아이디는 이메일 형식으로 입력해주세요.");
        }
    }

    private boolean isEmpty(final String value) {
        return value == null || value.isBlank();
    }

    private boolean isNotValidFormat(final String value) {
        return !EMAIL_FORMAT.matcher(value).matches();
    }

    public String getValue() {
        return value;
    }
}
