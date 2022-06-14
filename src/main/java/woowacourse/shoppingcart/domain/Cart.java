package woowacourse.shoppingcart.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cart {

    private final List<CartItem> items;

    public Cart(List<CartItem> items) {
        this.items = new ArrayList<>(items);
    }

    public boolean containsProductId(Long productId) {
        List<Long> productIds = items.stream()
                .map(CartItem::getProductId)
                .collect(Collectors.toList());

        return productIds.contains(productId);
    }
}
