package cart.controller.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class ProductRequest {

    @NotBlank(message = "상품명은 공백일 수 없습니다.")
    private final String name;
    @NotBlank(message = "Url은 공백일 수 없습니다.")
    private final String imageUrl;
    @Positive(message = "가격은 0보다 커야 합니다.")
    @Max(value = 1_000_000, message = "가격은 100만원보다 클 수 없습니다.")
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
