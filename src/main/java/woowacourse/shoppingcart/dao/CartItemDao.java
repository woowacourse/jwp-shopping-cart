package woowacourse.shoppingcart.dao;

public interface CartItemDao {
    Long addCartItem(final int customerId, final Long productId, int quantity);
}
