package cart.model;

import cart.exception.ErrorStatus;
import cart.exception.ItemException;

public class ItemUrl {

    private static final String PREFIX = "http";

    private final String url;

    public ItemUrl(String url) {
        validateUrl(url);
        this.url = url;
    }

    private void validateUrl(String url) {
        if (!url.contains(PREFIX)) {
            throw new ItemException(ErrorStatus.IMAGE_URL_PREFIX_ERROR);
        }
    }

    String getUrl() {
        return url;
    }
}
