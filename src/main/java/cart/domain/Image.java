package cart.domain;

public class Image {
    private final String url;

    public Image(String url) {
        validateUrl(url);
        this.url = url;
    }

    private void validateUrl(String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("이미지 URL은 비어있을 수 없습니다.");
        }
    }

    public String getUrl() {
        return url;
    }
}
