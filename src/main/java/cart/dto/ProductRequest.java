package cart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ProductRequest {

    @NotNull
    private final String name;
    private final String imageUrl;
    @Positive
    private final int price;

    public ProductRequest(final String name, final String imageUrl, final int price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }
}
