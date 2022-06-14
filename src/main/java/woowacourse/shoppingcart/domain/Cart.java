package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.ExistCartItemException;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<CartItem> cartItems;

    public Cart(List<CartItem> cartItems) {
        this.cartItems = new ArrayList<>(cartItems);
    }

    public void add(CartItem cartItem) {
        if (cartItems.contains(cartItem)) {
            throw new ExistCartItemException();
        }
        cartItems.add(cartItem);
    }

    public void add(Product product) {
        validateDuplicateProduct(product);
        final CartItem cartItem = new CartItem(product, 1);
        add(cartItem);
    }

    private void validateDuplicateProduct(Product product) {
        final boolean isDuplicateProduct = cartItems.stream()
                .anyMatch(it -> it.getProduct().equals(product));
        if (isDuplicateProduct) {
            throw new ExistCartItemException();
        }
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }
}
