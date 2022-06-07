package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import woowacourse.shoppingcart.domain.Product;

public class CreateProductRequest {

    @NotBlank
    private String name;
    @NotNull
    private Integer price;
    @NotBlank
    private String imageUrl;

    public CreateProductRequest() {
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
