package woowacourse.shoppingcart.dto.product;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import woowacourse.shoppingcart.domain.product.Product;

@Getter
public class ProductSaveRequest {
    @NotBlank
    private String name;
    @NotBlank
    private int price;
    private String image;

    public ProductSaveRequest() {
    }

    public ProductSaveRequest(String name, int price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Product toEntity() {
        return new Product(name, price, image);
    }
}
