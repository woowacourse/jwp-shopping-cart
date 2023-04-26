package cart.dto;

import javax.validation.constraints.NotNull;

public class ProductCreateDto {
    @NotNull
    private final String name;
    @NotNull
    private final String image;
    @NotNull
    private final Integer price;

    public ProductCreateDto(final String name, final String image, final Integer price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Integer getPrice() {
        return price;
    }
}
