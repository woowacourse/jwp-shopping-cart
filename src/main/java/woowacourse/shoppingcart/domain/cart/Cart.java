package woowacourse.shoppingcart.domain.cart;

import woowacourse.shoppingcart.domain.product.Product;

public class Cart {

    private final CartId cartId;
    private final Product product;
    private final Quantity quantity;

    public Cart(CartId cartId, Product product, Quantity quantity) {
        this.cartId = cartId;
        this.product = product;
        this.quantity = quantity;
    }

    public boolean isSame(Product product) {
        return product.isSame(this.product);
    }

    public CartId getId() {
        return cartId;
    }

    public Product getProduct() {
        return product;
    }

    public Quantity getQuantity() {
        return quantity;
    }
}
