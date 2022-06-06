package woowacourse.shoppingcart.ui.dto;

import woowacourse.shoppingcart.application.dto.ProductSaveRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class ProductAddRequest {

    @NotBlank(message = "[ERROR] 상품명은 공백일 수 없습니다.")
    String name;
    @Positive(message = "[ERROR] 가격은 양수입니다.")
    int price;
    @NotBlank(message = "[ERROR] 이미지 url은 공백일 수 없습니다.")
    String imageUrl;

    public ProductAddRequest() {
    }

    public ProductAddRequest(String name, int price, String imageUrl) {
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
