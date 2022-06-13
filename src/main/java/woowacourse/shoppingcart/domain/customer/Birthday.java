package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;
import woowacourse.auth.exception.BadRequestException;

public class Birthday {

    private static final String REGULAR_EXPRESSION = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
    private static final Pattern COMPILED_PATTERN = Pattern.compile(REGULAR_EXPRESSION);
    static final String INVALID_BIRTHDAY_FORMAT = "올바르지 않은 생년월일입니다.";

    private final String value;

    public Birthday(final String birthday) {
        validateBirthday(birthday);
        this.value = birthday;
    }

    private void validateBirthday(final String birthday) {
        if (!COMPILED_PATTERN.matcher(birthday).matches()) {
            throw new BadRequestException(INVALID_BIRTHDAY_FORMAT);
        }
    }

    public String getValue() {
        return value;
    }
}
