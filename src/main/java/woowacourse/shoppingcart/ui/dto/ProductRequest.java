package woowacourse.shoppingcart.ui.dto;

import woowacourse.shoppingcart.application.dto.ProductServiceRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ProductRequest {

    @NotBlank(message = "상품 이름은 빈 값일 수 없습니다.")
    private String name;
    @NotNull(message = "상품 가격은 빈 값일 수 없습니다.")
    @Positive(message = "상품 가격은 0이하의 수가 될 수 없습니다.")
    private Integer price;
    @NotBlank(message = "이미지 Url은 빈 값일 수 없습니다.")
    private String imageUrl;

    public ProductRequest() {
    }

    public ProductRequest(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductServiceRequest toServiceRequest() {
        return new ProductServiceRequest(name, price, imageUrl);
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
