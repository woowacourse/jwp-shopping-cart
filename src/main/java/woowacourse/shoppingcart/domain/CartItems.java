package woowacourse.shoppingcart.domain;

import java.util.Collections;
import java.util.List;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

public class CartItems {
    private final List<CartItem> cartItems;

    private CartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public static CartItems of(List<CartItem> cartItems) {
        return new CartItems(cartItems);
    }

    public void add(CartItem cartItem) {
        cartItems.stream()
                .filter(it -> it.getProductId().equals(cartItem.getProductId()))
                .findAny()
                .ifPresent(it -> {
                    throw new IllegalArgumentException("이미 장바구니에 상품이 존재합니다.");
                });
        cartItems.add(cartItem);
    }

    public CartItem findById(Long id) {
        return cartItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(InvalidCartItemException::new);
    }

    public List<CartItem> getCartItems() {
        return Collections.unmodifiableList(cartItems);
    }
}
