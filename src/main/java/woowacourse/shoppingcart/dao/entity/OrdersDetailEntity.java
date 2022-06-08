package woowacourse.shoppingcart.dao.entity;

import java.util.Map;
import woowacourse.shoppingcart.domain.order.OrderDetail;
import woowacourse.shoppingcart.domain.product.Product;

public class OrdersDetailEntity {

    private final Long id;
    private final Long ordersId;
    private final Long productId;
    private final int quantity;

    public OrdersDetailEntity(Long id, Long ordersId, Long productId, int quantity) {
        this.id = id;
        this.ordersId = ordersId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public static OrdersDetailEntity from(Long ordersId, OrderDetail orderDetail) {
        return new OrdersDetailEntity(
                orderDetail.getId(),
                ordersId,
                orderDetail.getProduct().getId(),
                orderDetail.getQuantity()
        );
    }

    public OrderDetail toOrderDetail(Map<Long, Product> productMap) {
        return new OrderDetail(id, productMap.get(productId), quantity);
    }

    public Long getId() {
        return id;
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
