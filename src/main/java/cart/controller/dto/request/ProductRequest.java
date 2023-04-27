package cart.controller.dto.request;

import cart.entity.ProductEntity;
import cart.exception.CantSellNegativeQuantity;

import javax.validation.constraints.NotEmpty;

public class ProductRequest {

    @NotEmpty(message = "Null을 허용하지 않습니다.")
    private String name;

    private int price;

    @NotEmpty(message = "Null을 허용하지 않습니다.")
    private String imageUrl;

    public ProductRequest() {
    }

    public ProductRequest(
            final String name,
            final int price,
            final String imageUrl
    ) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void validatePrice(final int price) {
        if (price < 0) {
            throw CantSellNegativeQuantity.EXCEPTION;
        }
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

    public ProductEntity toEntity() {
        return ProductEntity.generate(this);
    }
}
