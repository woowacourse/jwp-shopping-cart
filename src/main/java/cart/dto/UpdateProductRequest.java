package cart.dto;


import cart.domain.ProductImage;
import cart.domain.ProductName;
import cart.domain.ProductPrice;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateProductRequest {

    @NotNull
    private final Long id;

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

    public UpdateProductRequest(Long id, String name, int price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Long getId() {
        return id;
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
