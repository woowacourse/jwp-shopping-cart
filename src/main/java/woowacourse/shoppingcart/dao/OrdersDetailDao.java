package woowacourse.shoppingcart.dao;

public interface OrdersDetailDao {
    Long addOrdersDetail(final Long ordersId, final Long productId, final int quantity);
}
