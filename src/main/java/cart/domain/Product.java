package cart.domain;

import cart.exception.BusinessIllegalArgumentException;
import cart.ErrorCode;

public class Product {

    private final Long productId;
    private final Name name;
    private final String image;
    private final Price price;

    public Product(Long productId, String name, String image, long price) {
        this.productId = productId;
        this.name = Name.from(name);
        this.image = image;
        this.price = Price.from(price);
        validate(productId);
    }

    public long getProductId() {
        return productId;
    }

    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }


    private void validate(Long productId) {
        if (productId < 0) {
            throw new BusinessIllegalArgumentException(ErrorCode.NOT_VALID_ID);
        }
    }
}
