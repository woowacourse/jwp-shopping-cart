package cart.domain.cart;

import cart.domain.product.ProductId;

public class CartProduct {

    private final CartProductId cartProductId;
    private final ProductId productId;

    public CartProduct(final ProductId productId) {
        this(new CartProductId(), productId);
    }

    public CartProduct(final CartProductId cartProductId, final ProductId productId) {
        this.cartProductId = cartProductId;
        this.productId = productId;
    }

    public CartProductId getCartProductId() {
        return cartProductId;
    }

    public ProductId getProductId() {
        return productId;
    }
}
