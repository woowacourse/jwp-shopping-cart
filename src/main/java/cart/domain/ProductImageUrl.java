package cart.domain;

import java.util.Objects;

public class ProductImageUrl {

    public static final int MAX_IMAGE_URL_LENGTH = 2048;
    public static final String IMAGE_URL_LENGTH_ERROR_MESSAGE = "이미지 주소의 길이는 " + MAX_IMAGE_URL_LENGTH + "를 초과할 수 없습니다.";

    private final String imageUrl;

    public ProductImageUrl(final String imageUrl) {
        validate(imageUrl);
        this.imageUrl = imageUrl;
    }

    private void validate(final String imageUrl) {
        if (imageUrl != null && imageUrl.length() > MAX_IMAGE_URL_LENGTH) {
            throw new IllegalArgumentException(IMAGE_URL_LENGTH_ERROR_MESSAGE);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageUrl);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductImageUrl that = (ProductImageUrl) o;
        return Objects.equals(imageUrl, that.imageUrl);
    }

    public String getValue() {
        return imageUrl;
    }
}
