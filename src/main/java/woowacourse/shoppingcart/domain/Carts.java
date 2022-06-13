package woowacourse.shoppingcart.domain;

import java.util.List;
import java.util.stream.Collectors;

public class Carts {
    private final List<Cart> carts;

    public Carts(List<Cart> carts) {
        this.carts = carts;
    }

    public List<Long> getCartIds() {
        return carts.stream()
                .map(Cart::getId)
                .collect(Collectors.toList());
    }

    public List<Cart> getPlusQuantityCarts() {
        return carts.stream()
                .map(Cart::ofPlusQuantity)
                .collect(Collectors.toList());
    }

    public List<Long> findNotInProductIds(final List<Long> productIds) {
        List<Long> containedProductIds = getProductIds();
        return productIds.stream()
                .filter(productId -> !containedProductIds.contains(productId))
                .collect(Collectors.toList());
    }

    private List<Long> getProductIds() {
        return carts.stream()
                .map(Cart::getProductId)
                .collect(Collectors.toList());
    }
}
