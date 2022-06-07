package woowacourse.shoppingcart.domain;

public class ProductName {

    private static final int MAXIMUM_LENGTH = 50;

    private final String value;

    public ProductName(final String value) {
        validateName(value);
        this.value = value;
    }

    private void validateName(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("상품명은 비어있을 수 없습니다.");
        }
        if (value.length() > MAXIMUM_LENGTH) {
            throw new IllegalArgumentException(String.format("상품명은 %d자를 초과할 수 없습니다.", MAXIMUM_LENGTH));
        }
    }

    public String getValue() {
        return value;
    }
}
