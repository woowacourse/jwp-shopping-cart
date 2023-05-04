package cart.dto;

import cart.domain.product.entity.Product;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class ProductUpdateRequest {

    @NotBlank
    private Long id;

    @NotBlank
    private String name;

    @PositiveOrZero(message = "가격은 음수가 될 수 없습니다.")
    private int price;

    @NotBlank
    private String imageUrl;

    public ProductUpdateRequest() {
    }

    public ProductUpdateRequest(final Long id, final String name, final int price,
        final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product makeProduct() {
        return new Product(id, name, price, imageUrl, null, null);
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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
