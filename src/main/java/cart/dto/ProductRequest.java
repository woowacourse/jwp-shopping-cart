package cart.dto;

import cart.domain.product.ProductImage;
import cart.domain.product.ProductName;
import cart.domain.product.ProductPrice;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;

public class ProductRequest {

    @Size(
            min = ProductName.MIN_NAME_LENGTH,
            max = ProductName.MAX_NAME_LENGTH,
            message = ProductName.NAME_LENGTH_ERROR_MESSAGE)
    private final String name;

    @Range(
            min = ProductPrice.MIN_PRICE,
            max = ProductPrice.MAX_PRICE,
            message = ProductPrice.PRICE_ERROR_MESSAGE)
    private final int price;

    @Size(
            max = ProductImage.MAX_IMAGE_URL_LENGTH,
            message = ProductImage.IMAGE_URL_LENGTH_ERROR_MESSAGE)
    private final String image;

    public ProductRequest(final String name, final int price, final String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
