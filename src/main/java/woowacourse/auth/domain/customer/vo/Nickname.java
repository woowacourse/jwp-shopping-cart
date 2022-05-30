package woowacourse.auth.domain.customer.vo;

import java.util.Objects;

public class Nickname {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 10;
    private final String value;

    public Nickname(String value) {
        value = value.trim();
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("닉네임은 %d ~ %d자로 생성 가능합니다. 입력값: %s",
                            MIN_LENGTH, MAX_LENGTH, value));
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
