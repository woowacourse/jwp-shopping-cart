package cart.domain;

import java.util.Objects;

public class ProductImage {

    private static final int MAX_IMAGE_LENGTH = 2048;

    private final String value;

    public ProductImage(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (value == null) {
            throw new IllegalArgumentException("이미지 url은 null일 수 없습니다.");
        }
        if (value.length() > MAX_IMAGE_LENGTH) {
            throw new IllegalArgumentException("이미지 url은 " + MAX_IMAGE_LENGTH + "자 이하만 가능합니다.");
        }
        if (value.isBlank()) {
            throw new IllegalArgumentException("이미지 url은 공백일 수 없습니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductImage that = (ProductImage) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
