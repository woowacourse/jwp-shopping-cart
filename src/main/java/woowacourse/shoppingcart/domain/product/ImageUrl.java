package woowacourse.shoppingcart.domain.product;

public class ImageUrl {

    private static final int LENGTH_LIMIT = 2000;

    private final String value;

    public ImageUrl(String imageUrl) {
        validateImageUrlLength(imageUrl);
        this.value = imageUrl;
    }

    private void validateImageUrlLength(String imageUrl) {
        if (imageUrl.length() > LENGTH_LIMIT) {
            throw new IllegalArgumentException("url 길이는 2000자를 초과할 수 없습니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
