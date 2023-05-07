package cart.product.domain;

import java.util.Objects;

public class ImageUrl {
    private static final ImageUrl EMPTY_IMAGE_URL = new ImageUrl("Not image url");

    private final String url;

    private ImageUrl(final String url) {
        this.url = url;
    }

    public static ImageUrl from(final String url) {
        if (url == null) {
            return EMPTY_IMAGE_URL;
        }

        return new ImageUrl(url);
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
