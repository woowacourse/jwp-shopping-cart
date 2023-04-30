package cart.product.domain;

import lombok.Getter;

@Getter
public class ProductImageUrl {

    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 1000;

    private final String productImageUrl;

    public ProductImageUrl(final String productImageUrl) {
        final String stripped = productImageUrl.strip();
        validateLengthInRange(stripped);
        this.productImageUrl = stripped;
    }

    private void validateLengthInRange(final String productImageUrl) {
        if (productImageUrl.length() < MIN_LENGTH || productImageUrl.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("이미지 URL은 최소 %d, 최대 %d 글자입니다", MIN_LENGTH, MAX_LENGTH));
        }
    }
}
