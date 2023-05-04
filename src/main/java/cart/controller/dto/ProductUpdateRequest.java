package cart.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class ProductUpdateRequest {
    @NotNull
    @NotBlank
    private final String name;

    @PositiveOrZero
    private final long price;

    @NotNull
    @NotBlank
    private final String imageUrl;

    public ProductUpdateRequest(String name, long price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
