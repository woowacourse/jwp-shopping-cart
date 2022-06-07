package woowacourse.shoppingcart.domain;

public class ImageUrl {

    private static final int MAXIMUM_LENGTH = 2000;

    private final String value;

    public ImageUrl(final String value) {
        validateImageUrl(value);
        this.value = value;
    }

    private void validateImageUrl(final String value) {
        validateBlank(value);
        validateLength(value);
    }

    private void validateBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("이미지 주소는 비어있을 수 없습니다.");
        }
    }

    private void validateLength(String value) {
        if (value.length() > MAXIMUM_LENGTH) {
            throw new IllegalArgumentException(String.format("이미지 주소는 %d자를 초과할 수 없습니다.", MAXIMUM_LENGTH));
        }
    }

    public String getValue() {
        return value;
    }
}
