package woowacourse.shoppingcart.domain.user;

import woowacourse.shoppingcart.exception.InvalidNicknameException;

public class Nickname {

    private static final String NICKNAME_REGEX = "^[가-힣A-Za-z0-9]{2,8}$";
    private final String nickname;

    public Nickname(String nickname) {
        this.nickname = nickname;
        validate();
    }

    private void validate() {
        if (!nickname.matches(NICKNAME_REGEX)) {
            throw new InvalidNicknameException();
        }
    }

    public String value() {
        return nickname;
    }
}
