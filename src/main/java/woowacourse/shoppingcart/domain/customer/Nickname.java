package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Nickname {

    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[A-Za-z0-9ㄱ-ㅎㅏ-ㅣ가-힣]{1,10}$");

    private final String value;

    public Nickname(String value) {
        validateFormat(value);
        this.value = value;
    }

    private void validateFormat(String nickname) {
        Matcher nicknameMatcher = NICKNAME_PATTERN.matcher(nickname);
        if (!nicknameMatcher.matches()) {
            throw new IllegalArgumentException("닉네임 형식이 맞지 않습니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
