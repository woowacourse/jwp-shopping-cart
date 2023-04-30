package cart.domain.product;

import java.util.regex.Pattern;

public class ImageUrl {

    private static final Pattern PATTERN = Pattern.compile("^https?://.*");

    private final String url;

    private ImageUrl(final String url) {
        validate(url);

        this.url = url;
    }

    public static ImageUrl from(final String url) {
        return new ImageUrl(url);
    }

    private void validate(final String url) {
        if (!PATTERN.matcher(url).matches()) {
            throw new IllegalArgumentException("이미지 경로를 확인해주세요");
        }
    }

    public String getUrl() {
        return url;
    }
}
