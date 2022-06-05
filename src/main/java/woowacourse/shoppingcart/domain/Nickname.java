package woowacourse.shoppingcart.domain;

import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.IllegalNicknameException;

public class Nickname {

    private static final String REGEX = "[a-zA-Z0-9가-힣]{2,8}";

    private final String value;

    public Nickname(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!Pattern.matches(REGEX, value)) {
            throw new IllegalNicknameException();
        }
    }
}
