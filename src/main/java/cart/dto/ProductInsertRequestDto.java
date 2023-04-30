package cart.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

public class ProductInsertRequestDto {
    @NotBlank
    private final String name;
    @Pattern(regexp = ProductValidator.IMAGE_URL_REGEX)
    private final String image;
    @PositiveOrZero
    private final int price;

    public ProductInsertRequestDto(final String name, final String image, final int price) {
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

    public int getPrice() {
        return price;
    }

}
