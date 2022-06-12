package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.exception.UsernameValidationException;
import woowacourse.utils.StringValidator;

public class Username {

    private static final int USERNAME_MIN_LENGTH = 1;
    private static final int USERNAME_MAX_LENGTH = 10;

    private final String value;

    private Username() {
        this.value = null;
    }

    public Username(String value) {
        validateUsername(value);
        this.value = value;
    }

    public static Username empty() {
        return new Username();
    }

    private static void validateUsername(final String username) {
        StringValidator.validateNullOrBlank(username, new UsernameValidationException("닉네임에는 공백이 들어가면 안됩니다."));
        StringValidator.validateLength(USERNAME_MIN_LENGTH, USERNAME_MAX_LENGTH, username,
                new UsernameValidationException("닉네임은 1자 이상 10자 이하여야합니다."));
    }

    public String getValue() {
        return value;
    }
}
