package cart.catalog.domain;

public class Name {
    
    public static final String EMPTY_PRODUCT_NAME_ERROR = "상품 이름이 없습니다.";
    public static final String LONG_NAME_ERROR = "상품 이름은 10 글자를 넘을 수 없습니다";
    
    private final String value;
    
    public Name(final String value) {
        this.validate(value);
        this.value = value;
    }
    
    private void validate(final String value) {
        this.validateEmpty(value);
        this.validateLength(value);
    }
    
    private void validateLength(final String value) {
        if (value.length() > 10) {
            throw new IllegalArgumentException(LONG_NAME_ERROR);
        }
    }
    
    private void validateEmpty(final String value) {
        if (value.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_PRODUCT_NAME_ERROR);
        }
    }
    
    public String getValue() {
        return this.value;
    }
}
