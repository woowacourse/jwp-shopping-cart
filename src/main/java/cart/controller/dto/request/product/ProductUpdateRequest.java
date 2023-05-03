package cart.controller.dto.request.product;

import cart.domain.Product;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductUpdateRequest {

    @NotBlank(message = "이름을 다시 입력해주세요.")
    private final String name;

    @NotBlank(message = "이미지 주소를 다시 입력해주세요.")
    private final String imageUrl;

    @NotNull(message = "가격은 최소 100원, 최대 10,000,000원입니다.")
    @Min(100)
    @Max(10_000_000)
    private final Integer price;

    public ProductUpdateRequest(final String name, final String imageUrl, final Integer price) {
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

    public Integer getPrice() {
        return price;
    }

    public Product toProduct() {
        return new Product(name, imageUrl, price);
    }
}
