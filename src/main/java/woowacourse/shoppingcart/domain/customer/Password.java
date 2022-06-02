package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.exception.dataempty.CustomerDataEmptyException;
import woowacourse.shoppingcart.exception.dataformat.CustomerDataFormatException;

import java.util.regex.Pattern;

public class Password {

    private final static Pattern PASSWORD_FORMAT = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$");

    private final String value;

    public Password(final String value) {
        validatePassword(value);
        this.value = value;
    }

    private void validatePassword(final String value) {
        if (isEmpty(value)) {
            throw new CustomerDataEmptyException("비밀번호를 입력해주세요.");
        }
        if (isNotValidFormat(value)) {
            throw new CustomerDataFormatException("비밀번호는 영문, 한글, 숫자를 필수로 조합하여 8 ~ 16 자를 입력해주세요.");
        }
    }

    private boolean isEmpty(final String value) {
        return value == null || value.isBlank();
    }

    private boolean isNotValidFormat(final String value) {
        return !PASSWORD_FORMAT.matcher(value).matches();
    }

    public String getValue() {
        return value;
    }
}
