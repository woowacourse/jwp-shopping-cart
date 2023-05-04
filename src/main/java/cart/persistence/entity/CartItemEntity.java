package cart.persistence.entity;

import java.util.Objects;

public class CartItemEntity {

    private final Long cartItemId;
    private final Long cartId;
    private final Long productId;

    public CartItemEntity(Long cartItemId, Long cartId, Long productId) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.productId = productId;
    }

    public CartItemEntity(Long cartId, Long productId) {
        this(null, cartId, productId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemEntity that = (CartItemEntity) o;
        return Objects.equals(cartItemId, that.cartItemId) && Objects.equals(cartId, that.cartId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartItemId, cartId, productId);
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getProductId() {
        return productId;
    }
}
