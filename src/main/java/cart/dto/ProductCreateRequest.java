package cart.dto;

import cart.domain.product.entity.Product;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class ProductCreateRequest {

    @NotBlank
    private String name;

    @PositiveOrZero(message = "가격은 음수가 될 수 없습니다.")
    private int price;

    @NotBlank
    private String imageUrl;

    public ProductCreateRequest() {
    }

    public ProductCreateRequest(final String name, final int price, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product makeProduct() {
        return new Product(null, name, price, imageUrl, null, null);
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
