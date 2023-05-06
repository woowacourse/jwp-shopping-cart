package cart.domain.cart;

import java.util.ArrayList;
import java.util.List;

public class CartProducts {
    private final List<CartProduct> cartProducts;

    public CartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = new ArrayList<>(cartProducts);
    }

    public void add(CartProduct newCartProduct) {
        cartProducts.add(newCartProduct);
    }

    public void remove(Long cardItemId) {
        cartProducts.removeIf(cartItem -> cartItem.getId().equals(cardItemId));
    }

    public List<CartProduct> getCartItems() {
        return new ArrayList<>(cartProducts);
    }
}
