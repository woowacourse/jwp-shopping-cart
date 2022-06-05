package woowacourse.shoppingcart.dao;

public interface CartItemDao {
    Long addCartItem(final Long customerId, final Long productId, int quantity);
}
