package cart.domain;

import java.util.Objects;

public class ImageUrl {

    public static final int MAX_LENGTH = 5000;
    private final String value;

    public ImageUrl(final String value) {
        validateNotEmpty(value);
        validateLength(value);
        this.value = value;
    }

    private static void validateNotEmpty(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("이미지 URL은 빈 값일 수 없습니다.");
        }
    }

    private void validateLength(final String value) {
        if(value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("이미지 URL은 5000자 이하여야 합니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageUrl imageUrl = (ImageUrl) o;
        return Objects.equals(value, imageUrl.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ImageUrl{" +
                "value='" + value + '\'' +
                '}';
    }
}
