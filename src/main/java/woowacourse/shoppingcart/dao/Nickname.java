package woowacourse.shoppingcart.dao;

import woowacourse.shoppingcart.exception.badrequest.InvalidNicknameException;

public class Nickname {

    private final String value;

    public Nickname(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (!value.matches("[a-zA-Z0-9가-힣]{2,}")) {
            throw new InvalidNicknameException();
        }
    }
}
