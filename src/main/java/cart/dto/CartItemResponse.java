package cart.dto;

import cart.domain.CartItem;

public class CartItemResponse {
    private final Long id;

    private final ProductDto product;

    public CartItemResponse(Long id, ProductDto product) {
        this.id = id;
        this.product = product;
    }

    public static CartItemResponse from(CartItem cartItem) {
        ProductDto productDto = ProductDto.from(cartItem.getProduct());
        return new CartItemResponse(cartItem.getId(), productDto);
    }

    public Long getId() {
        return id;
    }

    public ProductDto getProduct() {
        return product;
    }
}
