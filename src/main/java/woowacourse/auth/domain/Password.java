package woowacourse.auth.domain;

import java.util.regex.Pattern;

public class Password {

    private static final Pattern FORMAT = Pattern
            .compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W])).*");

    private final String value;

    public Password(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!FORMAT.matcher(value).matches()) {
            throw new IllegalArgumentException("비밀번호는 대소문자, 숫자, 특수 문자를 포함해야 생성 가능합니다.");
        }
        if (value.length() < 8 || value.length() > 20) {
            throw new IllegalArgumentException("비밀번호는 8 ~ 20자로 생성 가능합니다.");
        }
    }
}
