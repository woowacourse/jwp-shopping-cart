package cart.service.product.domain;

public class ProductImage {

    private final String imageUrl;

    public ProductImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
