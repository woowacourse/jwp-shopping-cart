package cart.dto;

import cart.domain.ProductImageUrl;
import cart.domain.ProductName;
import cart.domain.ProductPrice;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

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
            max = ProductImageUrl.MAX_IMAGE_URL_LENGTH,
            message = ProductImageUrl.IMAGE_URL_LENGTH_ERROR_MESSAGE)
    private final String imageUrl;

    public ProductRequest(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
