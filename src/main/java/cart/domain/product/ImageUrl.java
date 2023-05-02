package cart.domain.product;

import java.util.Objects;

public class ImageUrl {
    private static final ImageUrl EMPTY_IMAGE_URL = new ImageUrl("Not image url");

    private final String url;

    private ImageUrl(String url) {
        this.url = url;
    }

    public static ImageUrl from(String url) {
        if (url == null) {
            return EMPTY_IMAGE_URL;
        }

        return new ImageUrl(url);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImageUrl imageUrl = (ImageUrl) o;
        return Objects.equals(getUrl(), imageUrl.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl());
    }

    public String getUrl() {
        return url;
    }
}
