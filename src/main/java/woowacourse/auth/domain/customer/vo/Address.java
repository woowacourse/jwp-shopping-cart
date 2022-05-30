package woowacourse.auth.domain.customer.vo;

import java.util.Objects;

public class Address {

    private static final int MAX_LENGTH = 255;
    private final String value;

    public Address(String value) {
        value = value.trim();
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        validateBlank(value);
        validateLength(value);
    }

    private void validateBlank(String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException(
                    String.format("주소는 빈 값 생성이 불가능합니다. 입력값 : %s", value));
        }
    }

    private void validateLength(String value) {
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("주소는 최대 %d자까지 가능합니다. 입력값 : %s", MAX_LENGTH, value));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(value, address.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
