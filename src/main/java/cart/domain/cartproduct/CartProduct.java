package cart.domain.cartproduct;

import cart.domain.cart.CartId;
import cart.domain.product.ProductId;

public class CartProduct {
    private CartProductId id;
    private CartId cartId;
    private ProductId productId;

    public CartProduct(final CartProductId id, final CartId cartId, final ProductId productId) {
        this(cartId, productId);
        this.id = id;
    }

    public CartProduct(final CartId cartId, final ProductId productId) {
        this.cartId = cartId;
        this.productId = productId;
    }

    public CartProductId getId() {
        return id;
    }

    public CartId getCartId() {
        return cartId;
    }

    public ProductId getProductId() {
        return productId;
    }
}
