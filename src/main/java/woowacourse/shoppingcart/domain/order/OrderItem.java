package woowacourse.shoppingcart.domain.order;

import woowacourse.shoppingcart.domain.cart.CartItem;

public class OrderItem {

    private final long productId;
    private final int count;

    public OrderItem(long productId, int count) {
        this.productId = productId;
        this.count = count;
    }

    public static OrderItem from(CartItem cartItem) {
        return new OrderItem(cartItem.getProductId(), cartItem.getCount());
    }

    public long getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }
}
