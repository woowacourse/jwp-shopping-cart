package woowacourse.shoppingcart.domain.product.vo;

import java.util.Objects;

public class Name {

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 50;

    private final String value;

    public Name(String value) {
        value = value.trim();
        validateLength(value);
        this.value = value;
    }

    private void validateLength(String value) {
        int length = value.length();
        if (length < MIN_LENGTH || length > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("상품의 이름은 %d자 ~ %d자만 가능합니다. 입력값: %s", MIN_LENGTH, MAX_LENGTH, value)
            );
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
        if (!(o instanceof Name)) {
            return false;
        }
        Name name = (Name) o;
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
