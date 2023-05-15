package cart.domain.product;

public class Image {

    private final String url;

    public Image(final String url) {
        validateIsBlank(url);
        this.url = url;
    }

    private void validateIsBlank(final String url) {
        if (url.isBlank()) {
            throw new IllegalArgumentException("url은 공백이 될 수 없습니다.");
        }
    }

    public String getUrl() {
        return url;
    }
}
