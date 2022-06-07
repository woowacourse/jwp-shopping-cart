 package woowacourse.shoppingcart.dao.entity;

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
