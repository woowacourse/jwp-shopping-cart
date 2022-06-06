package woowacourse.shoppingcart.domain.product;

import java.util.Objects;

public class ImageUrl {

    private static final String STARTING_WORD = "http";

    private final String value;

    public ImageUrl(String value) {
        validateImageUrl(value);
        this.value = value;
    }

    private void validateImageUrl(String value) {
        checkFormat(value);
        checkLength(value);
    }

    private void checkFormat(String value) {
        String startingWord = value.substring(0, 4);
        if (!STARTING_WORD.equals(startingWord)) {
            throw new IllegalArgumentException("상품 이미지 url 형식이 올바르지 않습니다. (형식: http로 시작)");
        }
    }

    private void checkLength(String value) {
        if (value.length() > 255) {
            throw new IllegalArgumentException("상품 이미지 url 길이가 올바르지 않습니다. (길이: 255자 이내)");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ImageUrl imageUrl = (ImageUrl)o;
        return Objects.equals(value, imageUrl.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
