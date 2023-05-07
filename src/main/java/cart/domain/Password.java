package cart.domain;

import java.util.Objects;

public class Password {

    public static final int MAX_LENGTH = 20;

    private final String value;

    public Password(String value) {
        validateNotEmpty(value);
        validateSize(value);
        this.value = value;
    }

    private void validateNotEmpty(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 빈 값일 수 없습니다.");
        }
    }

    private void validateSize(final String value) {
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("비밀번호는 " + MAX_LENGTH + "글자 이하여야 합니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Password{" +
                "value='" + value + '\'' +
                '}';
    }
}
