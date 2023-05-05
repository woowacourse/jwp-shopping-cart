package cart.product.domain;

import lombok.Getter;

@Getter
public class ProductName {

    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 30;

    private final String productName;

    public ProductName(final String productName) {
        final String stripped = productName.strip();
        validateLengthInRange(stripped);
        this.productName = stripped;
    }

    private void validateLengthInRange(final String productName) {
        if (productName.length() < MIN_LENGTH || productName.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("상품 이름은 최소 %d, 최대 %d 글자입니다", MIN_LENGTH, MAX_LENGTH));
        }
    }
}
