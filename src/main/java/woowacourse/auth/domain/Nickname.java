package woowacourse.auth.domain;

import java.util.Objects;

public class Nickname {

    private final String value;

    public Nickname(String value) {
        value = value.trim();
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (value.length() < 2 || value.length() > 10) {
            throw new IllegalArgumentException(
                    String.format("닉네임은 2 ~ 10자로 생성 가능합니다. 입력값: %s", value));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nickname)) {
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
