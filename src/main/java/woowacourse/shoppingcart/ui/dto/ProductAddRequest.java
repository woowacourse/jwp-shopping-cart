package woowacourse.shoppingcart.ui.dto;

import woowacourse.shoppingcart.application.dto.ProductSaveRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class ProductAddRequest {

    @NotBlank(message = "[ERROR] 상품명은 공백일 수 없습니다.")
    private String name;
    @Positive(message = "[ERROR] 가격은 양수입니다.")
    private int price;
    @NotBlank(message = "[ERROR] 이미지 url은 공백일 수 없습니다.")
    private String imageUrl;

    public ProductAddRequest() {
    }

    public ProductAddRequest(final String name, final int price, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductSaveRequest toServiceRequest() {
        return new ProductSaveRequest(name, price, imageUrl);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
