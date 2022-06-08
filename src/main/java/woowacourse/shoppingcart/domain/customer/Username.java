package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Username {

    private static final Pattern NAME_PATTERN = Pattern.compile("(\\S)+");

    private final String value;

    public Username(String value) {
        validateBlank(value);
        validateLength(value);

        this.value = value;
    }

    private void validateBlank(String value) {
        Matcher matcher = NAME_PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("이메일에는 공백이 들어가면 안됩니다.");
        }
    }

    private void validateLength(String value) {
        if (value.length() == 0 || value.length() > 10) {
            throw new IllegalArgumentException("닉네임 1자 이상 10자 이하여야합니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
