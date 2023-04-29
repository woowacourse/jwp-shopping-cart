package cart.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

public class ProductRequest {

    @NotBlank(message = "name 필드가 있어야 합니다.")
    private final String name;

    @NotNull(message = "price 필드가 있어야 합니다.")
    @PositiveOrZero(message = "price는 음수가 될 수 없습니다.")
    private final Integer price;

    @NotNull(message = "imageUrl 필드가 있어야 합니다.")
    @Pattern(
            message = "imageUrl형식에 맞지 않습니다.",
            regexp = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)"
    )
    private final String imageUrl;

    public ProductRequest(final String name, final Integer price, final String imageUrl) {
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
