package cart.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class ProductModifyRequest {

    @NotBlank(message = "상품의 이름은 공백일 수 없습니다.")
    private String name;

    @Positive(message = "상품의 가격은 0보다 커야 합니다.")
    private int price;

    @NotBlank(message = "이미지 url은 공백일 수 없습니다.")
    private String imageUrl;

    private ProductModifyRequest() {
    }

    public ProductModifyRequest(final String name, final int price, final String imageUrl) {
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
