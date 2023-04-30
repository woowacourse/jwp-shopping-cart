package cart.domain.product;

import java.util.regex.Pattern;

public class Image {

    private static final Pattern PATTERN = Pattern.compile("^https?://.*");

    private final String image;

    private Image(final String image) {
        validate(image);

        this.image = image;
    }

    public static Image from(final String image) {
        return new Image(image);
    }

    private void validate(final String image) {
        if (!PATTERN.matcher(image).matches()) {
            throw new IllegalArgumentException("이미지 경로를 확인해주세요");
        }
    }

    public String getImage() {
        return image;
    }
}
