package woowacourse.shoppingcart.domain.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.badrequest.InvalidNicknameException;

public class Nickname {

    public static final Pattern NICKNAME_PATTERN = Pattern.compile("^[가-힣A-Za-z0-9]{2,8}$");
    private final String nickname;

    public Nickname(String nickname) {
        this.nickname = nickname;
        validate();
    }

    private void validate() {
        Matcher matcher = NICKNAME_PATTERN.matcher(nickname);
        if (!matcher.matches()) {
            throw new InvalidNicknameException();
        }
    }

    public String value() {
        return nickname;
    }
}
