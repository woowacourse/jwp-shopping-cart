package cart.domain.cart;

import java.util.ArrayList;
import java.util.List;

public class CartProducts {

    private final List<CartProduct> cartProducts;

    public CartProducts() {
        this(new ArrayList<>());
    }

    public CartProducts(final List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }
}
