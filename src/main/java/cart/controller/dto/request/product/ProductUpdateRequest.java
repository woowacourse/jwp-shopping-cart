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
    private final String image;

    @NotNull(message = "가격은 최소 100원, 최대 10000000원입니다.")
    @Min(100)
    @Max(10_000_000)
    private final Integer price;

    public ProductUpdateRequest(final String name, final String image, final Integer price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Integer getPrice() {
        return price;
    }

    public Product toProduct() {
        return new Product(name, image, price);
    }
}
