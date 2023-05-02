package cart.domain.product;

import java.util.Objects;

public class ProductImage {

    public static final int MAX_IMAGE_URL_LENGTH = 2048;
    public static final String IMAGE_URL_LENGTH_ERROR_MESSAGE = "이미지 주소의 길이는 " + MAX_IMAGE_URL_LENGTH + "를 초과할 수 없습니다.";

    private final String image;

    public ProductImage(final String image) {
        validate(image);
        this.image = image;
    }

    private void validate(final String image) {
        if (image != null && image.length() > MAX_IMAGE_URL_LENGTH) {
            throw new IllegalArgumentException(IMAGE_URL_LENGTH_ERROR_MESSAGE);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(image);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImage that = (ProductImage) o;
        return Objects.equals(image, that.image);
    }

    public String getValue() {
        return image;
    }
}
