package cart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ProductRequest {

    @NotNull
    private final String image;

    @NotNull
    private final String name;

    @NotNull
    @Positive
    private final Integer price;

    public ProductRequest(String image, String name, Integer price) {
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }
}
