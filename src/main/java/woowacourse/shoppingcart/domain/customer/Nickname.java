package woowacourse.shoppingcart.domain.customer;

import org.springframework.util.StringUtils;
import woowacourse.shoppingcart.exception.InvalidArgumentRequestException;

public class Nickname {
    private static final int MAX_NICKNAME_LENGTH = 10;

    private final String nickname;

    public Nickname(String nickname) {
        validateNickname(nickname);
        this.nickname = nickname;
    }

    private void validateNickname(String nickname) {
        if (!StringUtils.hasText(nickname)) {
            throw new InvalidArgumentRequestException("닉네임은 공백일 수 없습니다.");
        }
        if (nickname.length() > MAX_NICKNAME_LENGTH) {
            throw new InvalidArgumentRequestException("닉네임의 길이는 10자 이하여야 합니다.");
        }
    }

    public String getNickname() {
        return nickname;
    }
}
