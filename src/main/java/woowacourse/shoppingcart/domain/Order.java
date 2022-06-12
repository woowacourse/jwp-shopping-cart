package woowacourse.shoppingcart.domain;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import woowacourse.common.exception.InvalidRequestException;

public class Order {

    private final List<CartItem> cartItems;

    public Order(List<CartItem> cartItems) {
        this.cartItems = toBuyingItems(cartItems);
    }

    private List<CartItem> toBuyingItems(List<CartItem> cartItems) {
        List<CartItem> buyingItems = cartItems.stream()
                .filter(CartItem::hasQuantityOverZero)
                .collect(Collectors.toList());
        if (buyingItems.isEmpty()) {
            throw new InvalidRequestException("주문할 상품이 선택되지 않았습니다.");
        }
        return buyingItems;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public List<Long> getProductIds() {
        return cartItems.stream()
                .map(CartItem::getProductId)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(cartItems, order.cartItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartItems);
    }
}
