package woowacourse.shoppingcart.domain.product;

import java.util.Objects;

public class Name {

    private final String value;

    public Name(String value) {
        validateName(value);
        this.value = value;
    }

    private void validateName(String value) {
        checkNull(value);
        checkLength(value);
    }

    private void checkNull(String value) {
        if (value == null) {
            throw new NullPointerException("상품명은 필수 입력 사항입니다.");
        }
    }

    private void checkLength(String value) {
        if (value.length() > 100) {
            throw new IllegalArgumentException("상품명 길이가 올바르지 않습니다. (길이: 100자 이내)");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Name name = (Name)o;
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
