package cart.service.dto.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ProductAddRequest {

    @NotBlank(message = "상품의 이름은 공백일 수 없습니다.")
    private String name;

    @NotNull
    @Positive(message = "상품의 가격은 0보다 커야 합니다.")
    private Integer price;

    @NotBlank(message = "이미지 url은 공백일 수 없습니다.")
    private String imageUrl;

    private ProductAddRequest() {
    }

    public ProductAddRequest(final String name, final Integer price, final String imageUrl) {
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
