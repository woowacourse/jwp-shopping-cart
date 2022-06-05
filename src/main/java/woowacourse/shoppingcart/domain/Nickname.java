package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidNicknameException;

public class Nickname {

    private final String nickname;

    public Nickname(String nickname) {
        this.nickname = nickname;
        if (!this.nickname.matches("^[가-힣A-Za-z0-9]{2,8}$")) {
            throw new InvalidNicknameException();
        }
    }
}
