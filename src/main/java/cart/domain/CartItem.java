package cart.domain;

public class CartItem {

    private final Long cartItemId;
    private final Product product;

    public CartItem(Long cartItemId, Product product) {
        this.cartItemId = cartItemId;
        this.product = product;
    }

    public CartItem(Product product) {
        this(null, product);
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public Product getProduct() {
        return product;
    }
}
