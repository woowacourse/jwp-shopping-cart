package cart.domain;

import java.net.MalformedURLException;
import java.net.URL;

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
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("적절한 형태의 url이 아닙니다.");
        }
    }

    public String getUrl() {
        return url;
    }
}
