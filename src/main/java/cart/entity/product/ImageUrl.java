package cart.entity.product;

import cart.exception.common.NullOrBlankException;

public class ImageUrl {

    private final String imageUrl;

    public ImageUrl(final String imageUrl) {
        validateNullOrBlank(imageUrl);
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    private void validateNullOrBlank(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new NullOrBlankException();
        }
    }
}
