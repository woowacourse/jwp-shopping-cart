package cart.domain.product;

import java.util.Objects;

public class ProductImageUrl {
    private static final int MAX_URL_LENGTH = 2083;

    private final String value;

    public ProductImageUrl(final String imageUrl) {
        validateUrl(imageUrl);
        this.value = imageUrl;
    }

    public String getValue() {
        return value;
    }

    private void validateUrl(final String url) {
        if (url.length() > MAX_URL_LENGTH) {
            throw new IllegalArgumentException(MAX_URL_LENGTH + "자 이하의 url을 입력해 주세요.");
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
