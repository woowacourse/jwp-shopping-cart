package cart.product.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class ProductUpdateRequestDto {
    @Positive
    private final int id;
    @Pattern(regexp = ProductValidator.IMAGE_URL_REGEX)
    private final String image;
    @NotBlank
    private final String name;
    @PositiveOrZero
    private final int price;

    public ProductUpdateRequestDto(final int id, final String image, final String name, final int price) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

}
