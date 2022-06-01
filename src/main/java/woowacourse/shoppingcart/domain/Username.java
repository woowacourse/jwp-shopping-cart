package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.util.StringUtil;

public class Username {

    private static final int MIN_USERNAME_LENGTH = 4;
    private static final int MAX_USERNAME_LENGTH = 20;

    private final String value;

    public Username(String value) {
        validateUsername(value);
        this.value = value;
    }

    private void validateUsername(String value) {
        StringUtil.validateLength(value, MIN_USERNAME_LENGTH, MAX_USERNAME_LENGTH);
    }

    public String getValue() {
        return value;
    }
}
