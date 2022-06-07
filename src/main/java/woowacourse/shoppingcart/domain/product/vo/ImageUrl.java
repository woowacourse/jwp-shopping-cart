package woowacourse.shoppingcart.domain.product.vo;

import java.util.Objects;

public class ImageUrl {

    private static final int MAX_LENGTH = 2000;

    private final String value;

    public ImageUrl(String value) {
        value = value.trim();
        validateLength(value);
        this.value = value;
    }

    private void validateLength(String value) {
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("이미지 url 은 %d자가 넘을 수 없습니다. 입력값: %s", MAX_LENGTH, value));
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageUrl)) {
            return false;
        }
        ImageUrl imageUrl = (ImageUrl) o;
        return Objects.equals(value, imageUrl.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
