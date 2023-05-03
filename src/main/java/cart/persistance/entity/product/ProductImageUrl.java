package cart.persistance.entity.product;

public class ProductImageUrl {

    private final String url;

    public ProductImageUrl(final String url) {
        validate(url);
        this.url = url;
    }

    private void validate(final String url) {
        validateIsNull(url);
        validateIsBlank(url);
    }

    private void validateIsNull(final String url) {
        if (url == null) {
            throw new IllegalArgumentException("[ERROR] 상품 이미지 url이 null입니다.");
        }
    }

    private void validateIsBlank(final String url) {
        if (url.strip().isBlank()) {
            throw new IllegalArgumentException("[ERROR] 상품 이미지 url이 비어있습니다.");
        }
    }

    public String getUrl() {
        return url;
    }
}
