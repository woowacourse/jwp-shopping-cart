package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.CannotUpdateUserNameException;
import woowacourse.shoppingcart.exception.IllegalFormException;

public class UserName {

    private final String value;

    private static final Pattern USER_NAME_PATTERN = Pattern.compile("^[a-z0-9_]{5,20}$");

    public UserName(final String value) {
        validatePattern(value);
        this.value = value;
    }

    private void validatePattern(final String value) {
        if (!USER_NAME_PATTERN.matcher(value).matches()) {
            throw new IllegalFormException("이름");
        }
    }

    public void validateChange(final String userName) {
        if (!this.value.equals(userName)) {
            throw new CannotUpdateUserNameException();
        }
    }

    public String getValue() {
        return value;
    }
}
