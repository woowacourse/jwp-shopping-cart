package woowacourse.auth.domain;

import java.util.regex.Pattern;

public class Nickname {

    private static final Pattern PATTERN = Pattern.compile("^[가-힣]{1,5}$");

    private final String value;

    public Nickname(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("닉네임 형식이 올바르지 않습니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
