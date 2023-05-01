package cart.domain;

import java.util.Objects;

public class ImageUrl {
    private static final int MAX_URL_RANGE = 2083;
    private static final String URL_PREFIX = "https";
    private final String value;

    public ImageUrl(final String imageUrl) {
        validateUrl(imageUrl);
        this.value = imageUrl;
    }

    public String getValue() {
        return value;
    }

    private void validateUrl(final String url) {
        if (url == null || !url.startsWith(URL_PREFIX)) {
            throw new IllegalArgumentException("https로 시작하는 URL 주소를 사용해주세요.");
        }
        if (url.length() > MAX_URL_RANGE) {
            throw new IllegalArgumentException("유효한 길이의 url을 입력해주세요.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ImageUrl imageUrl = (ImageUrl) o;
        return Objects.equals(value, imageUrl.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
