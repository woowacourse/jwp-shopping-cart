package cart.domain.cart;

import cart.domain.product.Product;
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

    public void add(final Product product) {
        if (cartProducts.stream().anyMatch(cartProduct -> cartProduct.getProduct().equals(product))) {
            throw new IllegalArgumentException("이미 장바구니에 담긴 상품입니다.");
        }
        cartProducts.add(new CartProduct(product));
    }

    public void delete(final Long cartProductId) {
        cartProducts.removeIf(cartProduct -> cartProduct.getCartProductId().getValue().equals(cartProductId));
    }
}
