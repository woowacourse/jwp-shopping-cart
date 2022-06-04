package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.exception.InvalidArgumentRequestException;

public class Nickname {
    private static final int MAX_NICKNAME_LENGTH = 10;

    private final String nickname;

    public Nickname(String nickname) {
        validateNickName(nickname);
        this.nickname = nickname;
    }

    private void validateNickName(String nickname) {
        if (nickname.isBlank()) {
            throw new InvalidArgumentRequestException("이름은 공백일 수 없습니다.");
        }
        if (nickname.length() > MAX_NICKNAME_LENGTH) {
            throw new InvalidArgumentRequestException("이름의 길이는 10자 이하여야 합니다.");
        }
    }

    public String getNickname() {
        return nickname;
    }
}
