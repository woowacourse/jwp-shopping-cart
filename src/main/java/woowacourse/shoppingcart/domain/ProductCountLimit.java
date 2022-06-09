package woowacourse.shoppingcart.domain;

public class ProductCountLimit {

    private static final int PRODUCT_COUNT_LIMIT_MIN_VALUE = 1;

    private final int value;

    public ProductCountLimit(int value) {
        validateMinValue(value);
        this.value = value;
    }

    private void validateMinValue(int value) {
        if (value < PRODUCT_COUNT_LIMIT_MIN_VALUE) {
            throw new IllegalArgumentException("한 페이지에 보여줄 상품의 개수는 양수여야합니다.");
        }
    }

    public int getValue() {
        return value;
    }
}
