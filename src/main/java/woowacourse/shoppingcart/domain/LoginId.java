package woowacourse.shoppingcart.domain;

import java.util.regex.Pattern;

public class LoginId {

    private static final Pattern LOGINID_PATTERN = Pattern.compile(
        "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");

    private final String value;

    public LoginId(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!LOGINID_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("유효한 이메일 형식이 아닙니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
