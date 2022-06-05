package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;

public class ProductRequest {

    @NotBlank(message = "상품 이름은 빈 값일 수 없습니다.")
    private String name;
    @NotBlank(message = "금액은 빈 값일 수 없습니다.")
    private int price;
    @NotBlank(message = "이미지 Url은 빈 값일 수 없습니다.")
    private String imageUrl;

    public ProductRequest() {
    }

    public ProductRequest(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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
