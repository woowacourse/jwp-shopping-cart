package cart.domain.product;

public class Price {
    
    public static final String NEGATIVE_PRICE_ERROR = "상품의 가격은 0보다 작을 수 없습니다.";
    private static final int LOWER_BOUNDARY = 0;

    private final int value;
    
    public Price(final int value) {
        validate(value);
        this.value = value;
    }
    
    private void validate(final int value) {
        if (value < LOWER_BOUNDARY) {
            throw new IllegalArgumentException(NEGATIVE_PRICE_ERROR);
        }
    }
    
    public int getValue() {
        return value;
    }
}
