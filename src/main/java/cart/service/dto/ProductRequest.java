package cart.service.dto;

import cart.entity.Product;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductRequest {

    @NotBlank(message = "이름이 비어있을 수는 없습니다.")
    private final String name;
    @NotBlank(message = "imageUrl이 비어있을 수 없습니다.")
    private final String imageUrl;
    @NotNull(message = "가격은 비어있을 수 없습니다.")
    private final Integer price;

    private ProductRequest() {
        this(null, null, null);
    }

    public ProductRequest(final String name, final String imageUrl, final Integer price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Product toProduct(final long id) {
        return new Product(id, name, imageUrl, price);
    }

    public Product toProduct() {
        return new Product(name, imageUrl, price);
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
}
