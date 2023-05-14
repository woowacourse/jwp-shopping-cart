package cart.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class ProductSaveRequest {

    @NotNull(message = "상품 이름은 null일 수 없습니다.")
    @NotBlank(message = "상품 이름은 비어있을 수 없습니다.")
    private final String name;

    @PositiveOrZero(message = "상품 가격은 0 이상이어야 합니다.")
    private final long price;

    @NotNull(message = "이미지 주소는 null일 수 없습니다.")
    @NotBlank(message = "이미지 주소는 비어있을 수 없습니다.")
    private final String imageUrl;

    public ProductSaveRequest(String name, long price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
