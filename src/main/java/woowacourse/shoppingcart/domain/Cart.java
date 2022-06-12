package woowacourse.shoppingcart.domain;

import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import woowacourse.common.exception.InvalidRequestException;

public class Cart {

    private final Map<Long, CartItem> cartItems;

    public Cart(List<CartItem> cartItems) {
        this.cartItems = toMapByProductId(cartItems);
    }

    public Cart(CartItem... cartItems) {
        this(List.of(cartItems));
    }

    private static Map<Long, CartItem> toMapByProductId(List<CartItem> cartItems) {
        return cartItems.stream()
                .collect(toMap(CartItem::getProductId, it -> it));
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems.values());
    }

    public boolean hasProduct(Product product) {
        return cartItems.containsKey(product.getId());
    }

    public List<CartItem> extractCartItems(List<Long> productIds) {
        validateRegistered(productIds);
        return productIds.stream()
                .map(cartItems::get)
                .collect(Collectors.toList());
    }

    private void validateRegistered(List<Long> productIds) {
        boolean isFullyRegistered = productIds.stream()
                .allMatch(cartItems::containsKey);
        if (!isFullyRegistered) {
            throw new InvalidRequestException("장바구니에 등록되지 않은 상품은 구매할 수 없습니다.");
        }
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
        return Objects.equals(cartItems, cart.cartItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartItems);
    }

    @Override
    public String toString() {
        return "Cart{" + "cartItems=" + cartItems + '}';
    }
}
