package cart.domain.product;

import java.util.Objects;

public class ProductImageUrl {
    private static final int MAX_LENGTH = 2083;

    private final String value;

    public ProductImageUrl(final String imageUrl) {
        validateImageUrl(imageUrl);
        this.value = imageUrl;
    }

    public String getValue() {
        return value;
    }

    private void validateImageUrl(final String imageUrl) {
        if (imageUrl.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(MAX_LENGTH + "자 이하의 URL을 입력해 주세요.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductImageUrl productImageUrl = (ProductImageUrl) o;
        return Objects.equals(value, productImageUrl.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
