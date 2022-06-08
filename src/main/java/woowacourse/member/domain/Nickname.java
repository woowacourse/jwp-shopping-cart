package woowacourse.member.domain;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Nickname nickname = (Nickname) o;
        return Objects.equals(value, nickname.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
