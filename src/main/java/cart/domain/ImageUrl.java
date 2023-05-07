package cart.domain;

import java.util.regex.Pattern;

public class ImageUrl {

    private static final Pattern urlPattern
            = Pattern.compile("^(http:\\/\\/|https:\\/\\/)?(www\\.)?[a-zA-Z0-9]+(\\.[a-zA-Z]+)+([/?].*)?$");

    private final String imageUrl;

    private ImageUrl(final String imageUrl) {
        validate(imageUrl);
        this.imageUrl = imageUrl;
    }

    public static ImageUrl from(final String imageUrl) {
        return new ImageUrl(imageUrl);
    }

    private void validate(final String imageUrl) {
        if (!urlPattern.matcher(imageUrl).matches()) {
            throw new IllegalArgumentException("이미지 주소가 올바르지 않습니다.");
        }
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
