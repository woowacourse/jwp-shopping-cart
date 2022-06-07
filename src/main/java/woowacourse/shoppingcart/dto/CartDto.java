package woowacourse.shoppingcart.dto;

import java.util.Objects;

public class CartDto {

    private final Long cartId;
    private final Long productId;
    private final int quantity;

    public CartDto(Long cartId, Long productId, int quantity) {
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartDto cartDto = (CartDto) o;
        return quantity == cartDto.quantity && Objects.equals(cartId, cartDto.cartId) && Objects.equals(
                productId, cartDto.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, productId, quantity);
    }
}
