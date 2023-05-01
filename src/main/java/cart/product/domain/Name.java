package cart.product.domain;

public class Name {
    
    public static final String EMPTY_PRODUCT_NAME_ERROR = "상품 이름이 없습니다.";
    public static final String LONG_NAME_ERROR = "상품 이름은 10 글자를 넘을 수 없습니다";
    private static final int LENGTH_UPPER_BOUNDARY = 10;

    private final String value;
    
    public Name(final String value) {
        validate(value);
        this.value = value;
    }
    
    private void validate(final String value) {
        validateEmpty(value);
        validateLength(value);
    }
    
    private void validateLength(final String value) {
        if (value.length() > LENGTH_UPPER_BOUNDARY) {
            throw new IllegalArgumentException(LONG_NAME_ERROR);
        }
    }
    
    private void validateEmpty(final String value) {
        if (value.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_PRODUCT_NAME_ERROR);
        }
    }
    
    public String getValue() {
        return value;
    }
}
