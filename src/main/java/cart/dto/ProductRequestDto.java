package cart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class ProductRequestDto {

    @NotNull(message = "name은 null이면 안됩니다.")
    private final String name;
    @NotNull(message = "image가 null이면 안됩니다.")
    private final String image;
    @NotNull(message = "price가 null이면 안됩니다.")
    @PositiveOrZero(message = "가격은 음수가 될 수 없습니다.")
    private final Integer price;

    public ProductRequestDto(final String name, final String image, final Integer price) {
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

    @Override
    public String toString() {
        return "ProductRequestDto{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                '}';
    }
}