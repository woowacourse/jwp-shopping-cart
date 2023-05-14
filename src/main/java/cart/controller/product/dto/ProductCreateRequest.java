package cart.controller.product.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ProductCreateRequest {

    @NotNull(message = "이미지 url은 비어있을 수 없습니다.")
    private String imageUrl;

    @NotNull(message = "상품 이름은 비어있을 수 없습니다.")
    @NotBlank(message = "상품 이름은 최소 한 글자 입니다.")
    private String name;

    @NotNull(message = "가격은 비어있을 수 없습니다.")
    @Positive(message = "가격은 0 이상이어야 합니다.")
    private Integer price;

    public ProductCreateRequest(String imageUrl, String name, Integer price) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
    }

    public ProductCreateRequest() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }
}
