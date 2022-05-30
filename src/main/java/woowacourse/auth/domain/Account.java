package woowacourse.auth.domain;

import java.util.regex.Pattern;

public class Account {

    private static final Pattern FORMAT = Pattern.compile("^[0-9a-z]*");

    private final String value;

    public Account(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!FORMAT.matcher(value).matches()) {
            throw new IllegalArgumentException(
                    String.format("계정은 소문자와 숫자로 생성 가능합니다. 입력값: %s", value));
        }
    }

}
