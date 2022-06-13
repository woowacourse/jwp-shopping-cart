package woowacourse.shoppingcart.domain.product;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageUrl {

    private static final Pattern IMAGE_URL_PATTERN = Pattern.compile("http.*");

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
        Matcher matcher = IMAGE_URL_PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("상품 이미지 url 형식이 올바르지 않습니다. (형식: http로 시작)");
        }
    }

    private void checkLength(String value) {
        if (value.length() > 1024) {
            throw new IllegalArgumentException("상품 이미지 url 길이가 올바르지 않습니다. (길이: 1024자 이내)");
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
