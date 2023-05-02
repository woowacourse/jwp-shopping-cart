package cart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ProductRequestDto {

    @NotNull
    private final String name;

    @Positive
    private final Integer price;

    @NotNull
    private final String imageUrl;

    public ProductRequestDto(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
