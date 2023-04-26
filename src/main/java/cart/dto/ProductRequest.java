package cart.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ProductRequest {

    @NotNull(message = "이미지 url은 비어있을 수 없습니다.")
    private final String image;

    @NotNull(message = "이름은 비어있을 수 없습니다.")
    private final String name;

    @NotNull(message = "가격은 비어있을 수 없습니다.")
    @Positive(message = "가격은 0 이상이어야 합니다.")
    @Max(value = 1_000_000_000, message = "가격은 최대 10억까지 가능합니다.")
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
