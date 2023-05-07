package cart.domain.cart;

import java.util.List;
import java.util.Objects;

public class Cart {
    private Long cartNo;
    private List<CartProduct> cartProducts;

    public Cart(Long cartNo, List<CartProduct> cartProducts) {
        this.cartNo = cartNo;
        this.cartProducts = cartProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cart cart = (Cart) o;
        return Objects.equals(cartNo, cart.cartNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartNo);
    }

    public Long getCartNo() {
        return cartNo;
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }
}
