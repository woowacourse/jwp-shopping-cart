package cart.entiy.cart;

import cart.domain.cart.CartId;
import cart.domain.cart.CartProduct;
import cart.domain.cart.CartProductId;
import cart.domain.product.ProductId;

public class CartProductEntity {

    private final CartProductId cartProductId;
    private final CartId cartId;
    private final ProductId productId;

    public CartProductEntity(final CartProductId cartProductId,
            final CartId cartId,
            final ProductId productId) {
        this.cartProductId = cartProductId;
        this.cartId = cartId;
        this.productId = productId;
    }

    public CartProductEntity(final Long cartProductId,
            final Long cartId,
            final Long productId) {
        this(new CartProductId(cartProductId),
                new CartId(cartId),
                new ProductId(productId));
    }


    public CartId getCartEntityId() {
        return cartId;
    }

    public CartProductId getCartProductEntityId() {
        return cartProductId;
    }

    public ProductId getProductEntityId() {
        return productId;
    }

    public CartProduct toDomain() {
        return new CartProduct(cartProductId, productId);
    }
}
