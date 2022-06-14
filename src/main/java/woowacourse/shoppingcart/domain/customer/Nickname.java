package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;

public class Nickname {

    private static final String NICKNAME_REGEX = "^[A-Za-z0-9ㄱ-ㅎㅏ-ㅣ가-힣]{1,10}$";

    private final String value;

    public Nickname(String value) {
        validateNickname(value);
        this.value = value;
    }

    public void validateNickname(String nickname) {
        if (nickname.isBlank() || !Pattern.matches(NICKNAME_REGEX, nickname)) {
            throw new IllegalArgumentException("[ERROR] 닉네임 기본 형식에 어긋납니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
