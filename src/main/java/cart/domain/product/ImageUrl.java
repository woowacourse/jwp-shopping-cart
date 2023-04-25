package cart.domain.product;

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

    public String getUrl() {
        return url;
    }
}
