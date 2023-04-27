package cart.domain;

import cart.exception.ErrorMessage;

public class Product {

    private Long productId;
    private final String name;
    private final String image;
    private final Long price;

    public Product(Long productId, String name, String image, long price) {
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public long getPrice() {
        return price;
    }

    private void validate(Long productId, String name, String image, long price) {
        if (productId < 0) {
            throw new IllegalArgumentException(ErrorMessage.NOT_VALID_ID.getMessage());
        }
        if (price < 0) {
            throw new IllegalArgumentException(ErrorMessage.NOT_VALID_PRICE.getMessage());
        }
    }
}
