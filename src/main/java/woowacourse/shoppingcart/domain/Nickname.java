package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.util.StringUtil;

public class Nickname {

    private static final int MIN_NICKNAME_LENGTH = 1;
    private static final int MAX_NICKNAME_LENGTH = 10;
    private final String value;

    public Nickname(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        StringUtil.validateLength(value, MIN_NICKNAME_LENGTH, MAX_NICKNAME_LENGTH);
    }

    public String getValue() {
        return value;
    }
}
