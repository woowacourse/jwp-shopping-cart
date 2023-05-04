package cart.domain;

import java.util.Objects;

public class ImageUrl {

    private final String imageUrl;

    public ImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        validate(this.imageUrl);
    }

    private void validate(String imageUrl) {
        if(imageUrl.isBlank()) {
            throw new IllegalArgumentException("상품 사진 url은 공백일 수 없습니다.");
        }
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImageUrl imageUrl1 = (ImageUrl) o;
        return Objects.equals(imageUrl, imageUrl1.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageUrl);
    }
}
