package cart.domain;

import cart.exception.BusinessIllegalArgumentException;
import cart.ErrorCode;

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
        validate(productId, price);
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

    private void validate(Long productId, long price) {
        if (productId < 0) {
            throw new BusinessIllegalArgumentException(ErrorCode.NOT_VALID_ID);
        }
        if (price < 0) {
            throw new BusinessIllegalArgumentException(ErrorCode.NOT_VALID_PRICE);
        }
    }
}
