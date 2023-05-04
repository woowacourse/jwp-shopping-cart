package cart.domain;

import java.util.Objects;

public class Email {

    private static final int MAX_LENGTH = 100;

    private final String value;

    public Email(final String value) {
        validateNotEmpty(value);
        validateLength(value);
        this.value = value;
    }

    private static void validateNotEmpty(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("이메일은 빈 값일 수 없습니다.");
        }
    }

    private void validateLength(final String value) {
        if(value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("이메일은 100자 이하여야 합니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Email{" +
                "value='" + value + '\'' +
                '}';
    }
}
