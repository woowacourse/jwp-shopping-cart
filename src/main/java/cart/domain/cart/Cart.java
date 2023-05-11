package cart.domain.cart;

import cart.domain.product.Product;

public class Cart {
    private final Product product;
    private final Long id;

    public Cart(Product product, Long id) {
        this.product = product;
        this.id = id;
    }

    public Cart(Product product) {
        this(product, null);
    }

    public Product getProduct() {
        return product;
    }

    public Long getId() {
        return id;
    }
}
