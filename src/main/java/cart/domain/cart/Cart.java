package cart.domain.cart;

import cart.domain.product.Product;

public class Cart {

    private final Long cartId;
    private final Product product;

    public Cart(final Long cartId, final Product product) {
        this.cartId = cartId;
        this.product = product;
    }

    public Long getCartId() {
        return cartId;
    }

    public Product getProduct() {
        return product;
    }
}
