package cart.dto.cart;

import cart.dto.product.ProductDto;

public class CartProductDto {
    private Long cartId;
    private ProductDto product;

    public CartProductDto(Long cartId, ProductDto product) {
        this.cartId = cartId;
        this.product = product;
    }

    public Long getCartId() {
        return cartId;
    }

    public ProductDto getProduct() {
        return product;
    }
}
