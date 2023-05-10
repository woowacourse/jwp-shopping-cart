package cart.domain.member;

import java.util.regex.Pattern;

public class Nickname {

    private static final String REGEX = "^[0-9가-힣a-zA-Z]{2,8}$";

    private final String nickname;

    public Nickname(String nickname) {
        validate(nickname);
        this.nickname = nickname;
    }

    private void validate(String nickname) {
        if (!Pattern.matches(REGEX, nickname)) {
            throw new IllegalArgumentException("닉네임은 한글, 영어, 숫자로 된 2자 이상 8자 이하여야합니다.");
        }
    }

    public String getNickname() {
        return nickname;
    }
}
