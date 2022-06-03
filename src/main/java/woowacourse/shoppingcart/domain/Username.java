package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidUsernameException;

public class Username {

    private final String value;

    public Username(String value) {
        validateSpace(value);
        validateLength(value);
        this.value = value;
    }

    private void validateSpace(String value) {
        if (value.contains(" ")) {
            throw new InvalidUsernameException("회원 이름에는 공백이 들어갈 수 없습니다.");
        }
    }

    private void validateLength(String value) {
        if (value.length() > 32) {
            throw new InvalidUsernameException("회원 이름은 32자 이하입니다.");
        }
    }
}
