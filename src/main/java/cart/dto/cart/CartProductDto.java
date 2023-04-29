package cart.dto.cart;

import cart.dto.product.ProductDto;

public class CartProductDto {
    private Long id;
    private ProductDto product;

    public CartProductDto(Long id, ProductDto product) {
        this.id = id;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public ProductDto getProduct() {
        return product;
    }
}
