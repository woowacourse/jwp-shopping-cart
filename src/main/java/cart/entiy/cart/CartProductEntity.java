package cart.entiy.cart;

import cart.domain.cart.CartProductId;
import cart.domain.product.ProductId;
import cart.entiy.product.ProductEntityId;

public class CartProductEntity {

    private final CartProductEntityId cartProductEntityId;
    private final CartEntityId cartEntityId;
    private final ProductEntityId productEntityId;

    public CartProductEntity(final CartProductEntityId cartProductEntityId,
            final CartEntityId cartEntityId,
            final ProductEntityId productEntityId) {
        this.cartProductEntityId = cartProductEntityId;
        this.cartEntityId = cartEntityId;
        this.productEntityId = productEntityId;
    }

    public CartProductEntity(final CartProductId cartProductId,
            final CartEntityId cartEntityId,
            final ProductId productId) {
        this(CartProductEntityId.from(cartProductId),
                cartEntityId,
                ProductEntityId.from(productId));
    }

    public CartProductEntity(final Long cartProductEntityId,
            final Long cartEntityId,
            final Long productEntityId) {
        this(new CartProductEntityId(cartProductEntityId),
                new CartEntityId(cartEntityId),
                new ProductEntityId(productEntityId));
    }


    public CartEntityId getCartEntityId() {
        return cartEntityId;
    }

    public CartProductEntityId getCartProductEntityId() {
        return cartProductEntityId;
    }

    public ProductEntityId getProductEntityId() {
        return productEntityId;
    }
}
