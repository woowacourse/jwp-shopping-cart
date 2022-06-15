package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import woowacourse.shoppingcart.domain.Product;

public class CreateProductRequest {

    @NotBlank(message = "상품 이름을 입력해주세요😉")
    private String name;
    @NotNull(message = "상품 가격을 입력해주세요😉")
    private Integer price;
    @NotBlank(message = "상품 이미지를 입력해주세요😉")
    private String imageUrl;

    private CreateProductRequest() {
    }

    public CreateProductRequest(final String name, final Integer price, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product toProduct() {
        return new Product(name, price, imageUrl);
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
