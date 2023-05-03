package cart.domain;

import cart.ErrorCode;
import cart.domain.generic.Name;
import cart.domain.generic.Price;
import cart.exception.BusinessIllegalArgumentException;

public class Product {

    private final Long productId;
    private final Name name;
    private final String image;
    private final Price price;

    public Product(String name, String image, long price) {
        this(null, name, image, price);
    }

    public Product(Long productId, String name, String image, long price) {
        validate(productId);
        this.productId = productId;
        this.name = Name.from(name);
        this.image = image;
        this.price = Price.from(price);
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

    public boolean isIdNull() {
        return this.productId == null;
    }

    private void validate(Long productId) {
        if (productId != null && productId < 0) {
            throw new BusinessIllegalArgumentException(ErrorCode.NOT_VALID_ID);
        }
    }
}
