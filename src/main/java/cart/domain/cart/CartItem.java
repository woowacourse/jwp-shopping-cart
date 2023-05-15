package cart.domain.cart;

import cart.domain.product.Product;

public class CartItem {

    private final Long id;
    private final Product product;

    public CartItem(final Long id, final Product product) {
        this.id = id;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }
}
