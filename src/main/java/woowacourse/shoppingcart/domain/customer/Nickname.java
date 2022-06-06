package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

public final class Nickname {
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 10;
    private final String value;

    private Nickname(String value) {
        validate(value);
        this.value = value;
    }

    public static Nickname of(String value) {
        return new Nickname(value);
    }

    private void validate(String value) {
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("닉네임의 길이는 2~10자이어야 합니다.");
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
