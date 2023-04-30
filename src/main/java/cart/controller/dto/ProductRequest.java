package cart.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class ProductRequest {

    @NotBlank(message = "상품 이름에 공백을 입력할 수 없습니다.")
    private final String name;

    @NotBlank(message = "상품 사진의 url에 공백을 입력할 수 없습니다.")
    private final String imageUrl;

    @PositiveOrZero(message = "상품 금액은 0이상만 가능합니다.")
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
