package woowacourse.auth.domain;

import java.util.regex.Pattern;

public class Password {

    private static final Pattern FORMAT = Pattern.compile(
            "(([a-z]+)(\\\\d+))\\\\w*"
                    + "|((\\\\d+)([a-z]+))\\\\w*"
                    + "|(([A-Z]+)(a\\\\d+))\\\\w*"
                    + "|((\\\\d+)([A-Z]+))\\\\w*"
                    + "|(([a-z]+)([A-Z]+))\\\\w*"
                    + "|(([A-Z]+)([a-z]+))\\\\w*");

    private final String value;

    public Password(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!FORMAT.matcher(value).matches()) {
            throw new IllegalArgumentException(
                    String.format("비밀번호는 대문자, 소문자, 숫자 중 2종류 이상으로 생성 가능합니다. 입력값: %s", value));
        }
    }
}
