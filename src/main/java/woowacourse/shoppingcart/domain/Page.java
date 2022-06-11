package woowacourse.shoppingcart.domain;

public class Page {

    private static final int PAGE_MIN_VALUE = 1;

    private final int value;

    public Page(int value) {
        validateMinValue(value);
        this.value = value;
    }

    private void validateMinValue(int value) {
        if (value < PAGE_MIN_VALUE) {
            throw new IllegalArgumentException("page 값은 양수여야 합니다.");
        }
    }

    public int calculateOffset(int productCountLimit) {
        return (value - 1) * productCountLimit;
    }
}
