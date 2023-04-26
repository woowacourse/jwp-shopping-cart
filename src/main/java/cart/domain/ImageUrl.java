package cart.domain;

import java.util.Objects;

public class ImageUrl {

    public static final int MAX_LENGTH = 5000;
    private final String value;

    public ImageUrl(final String value) {
        validateLength(value);
        this.value = value;
    }

    private void validateLength(final String value) {
        // TODO: 2023/04/26 nullable
        if (value.isEmpty()) {
            return;
        }
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
