package cart.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class ModifyRequest {
    private final String name;
    private final long price;
    private final String imageUrl;

    public ModifyRequest(String name, long price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    @NotNull
    @NotBlank
    public String getName() {
        return name;
    }

    @PositiveOrZero
    public long getPrice() {
        return price;
    }

    @NotNull
    @NotBlank
    public String getImageUrl() {
        return imageUrl;
    }
}
