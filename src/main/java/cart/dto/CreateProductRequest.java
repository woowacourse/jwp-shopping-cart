package cart.dto;

import cart.domain.Product;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

public class CreateProductRequest {

    @Size(
            min = Product.MIN_NAME_LENGTH,
            max = Product.MAX_NAME_LENGTH,
            message = Product.NAME_LENGTH_ERROR_MESSAGE)
    private final String name;

    @Max(
            value = Product.MAX_PRICE,
            message = Product.PRICE_ERROR_MESSAGE)
    private final int price;

    private final String image;

    public CreateProductRequest(String name, int price, String image) {
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
