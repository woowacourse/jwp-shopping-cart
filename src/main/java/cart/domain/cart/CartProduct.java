package cart.domain.cart;

import cart.domain.product.Product;

public class CartProduct {

    private final CartProductId cartProductId;
    private final Product product;

    public CartProduct(final CartProductId cartProductId, final Product product) {
        this.cartProductId = cartProductId;
        this.product = product;
    }

    public CartProduct(final Product product) {
        this(new CartProductId(), product);
    }

    public CartProductId getCartProductId() {
        return cartProductId;
    }

    public Product getProduct() {
        return product;
    }
}
