package cart.domain.product;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class ImageUrl {
    private static final String JPG_FORMAT = ".jpg";
    private static final String PNG_FORMAT = ".png";
    private static final int MAX_URL_LENGTH = 100;
    private final URL imageUrl;

    private ImageUrl(URL imageUrl) {
        validateImageFormat(imageUrl);
        this.imageUrl = imageUrl;
    }

    private void validateImageFormat(URL imageUrl) {
        String path = imageUrl.getPath();
        if (!path.endsWith(JPG_FORMAT) && !path.endsWith(PNG_FORMAT)) {
            throw new IllegalArgumentException("이미지의 확장자는 JPG 또는 PNG만 가능합니다.");
        }
    }

    public static ImageUrl from(String url) {
        validateBlank(url);
        validateLength(url);
        try {
            return new ImageUrl(new URL(url));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("올바른 URL 형식이 아닙니다.");
        }
    }

    private static void validateBlank(String url) {
        if (Objects.isNull(url) || url.isBlank()) {
            throw new IllegalArgumentException("URL은 빈 값이 될 수 없습니다.");
        }
    }

    private static void validateLength(String url) {
        int urlLength = url.length();
        if (urlLength > MAX_URL_LENGTH) {
            throw new IllegalArgumentException("URL의 길이는 " + MAX_URL_LENGTH + "자리 이하여야 합니다.");
        }
    }

    public URL getImageUrl() {
        return imageUrl;
    }
}
