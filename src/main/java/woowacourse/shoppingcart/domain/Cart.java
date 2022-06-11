package woowacourse.shoppingcart.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.exception.IllegalCartItemException;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

public class Cart {
    private final Set<CartItem> items;

    public Cart() {
        this.items = new HashSet<>();
    }

    public Cart(List<CartItem> items) {
        this.items = new HashSet<>(items);
    }

    public void addItem(CartItem item) {
        boolean added = items.add(item);
        if (!added) {
            throw new IllegalCartItemException("이미 담긴 상품입니다.");
        }
    }

    public void changeQuantity(Long productId, int quantity) {
        CartItem addingItem = getItemBy(productId);
        addingItem.changeQuantity(quantity);
    }

    public Orders checkOut(List<Long> productIds) {
        List<CartItem> orderedItems = filterOrderedItems(productIds);

        List<OrderDetail> orderDetails = orderedItems.stream()
                .map(CartItem::checkOut)
                .collect(Collectors.toList());

        remove(orderedItems);
        return new Orders(orderDetails);
    }

    private List<CartItem> filterOrderedItems(List<Long> productIds) {
        return items.stream()
                .filter(filterBy(productIds))
                .collect(Collectors.toList());
    }

    private Predicate<CartItem> filterBy(List<Long> productIds) {
        return (cartItem) -> productIds.stream()
                .anyMatch(cartItem::isProductId);
    }

    private void remove(List<CartItem> items) {
        for (CartItem item : items) {
            this.items.remove(item);
        }
    }

    public Set<CartItem> getItems() {
        return Collections.unmodifiableSet(items);
    }

    public CartItem getItemBy(Long productId) {
        return items.stream()
                .filter(cartItem -> cartItem.isProductId(productId))
                .findAny()
                .orElseThrow(() -> new InvalidCartItemException("장바구니에 추가되지 않은 상품입니다."));
    }
}
