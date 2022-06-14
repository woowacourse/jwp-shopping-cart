package woowacourse.shoppingcart.domain.customer;

import woowacourse.exception.InvalidNicknameFormatException;

public class Nickname {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 10;

    private final String nickname;

    public Nickname(final String nickname) {
        validateLength(nickname);
        validateNotBlank(nickname);
        this.nickname = nickname;
    }

    private void validateLength(final String nickname) {
        int length = nickname.length();
        if (length < MIN_LENGTH || length > MAX_LENGTH) {
            throw new InvalidNicknameFormatException();
        }
    }

    private void validateNotBlank(final String nickname) {
        if (nickname.isBlank()) {
            throw new InvalidNicknameFormatException();
        }
    }

    public String getNickname() {
        return nickname;
    }
}
