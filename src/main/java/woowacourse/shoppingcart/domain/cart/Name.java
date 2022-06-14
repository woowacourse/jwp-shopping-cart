package woowacourse.shoppingcart.domain.cart;

import java.util.Objects;

class Name {

    private final String value;

    Name(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        checkNull(value);
        checkLength(value);
    }

    private void checkNull(String value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("상품 이름은 Null 일 수 없습니다.");
        }
    }

    private void checkLength(String value) {
        int length = value.length();
        if (length < 1 || length > 100) {
            throw new IllegalArgumentException("상품 이름은 1자 이상 100자 이하여야합니다.");
        }
    }

    String getValue() {
        return value;
    }
}
