package cart.domain;

import java.util.Collections;
import java.util.List;

public class Cart {

    private final List<CartProduct> cartProducts;

    public Cart(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public void deleteProduct(final CartProduct cartProduct) {
        if (!cartProducts.contains(cartProduct)) {
            throw new IllegalArgumentException("카트에 존재하지 않는 상품입니다.");
        }
        cartProducts.remove(cartProduct);
    }

    public List<CartProduct> getCartProducts() {
        return Collections.unmodifiableList(cartProducts);
    }
}
