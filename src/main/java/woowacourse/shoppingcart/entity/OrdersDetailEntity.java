package woowacourse.shoppingcart.entity;

public class OrdersDetailEntity {
    private final Long id;
    private final Long ordersId;
    private final Long productId;
    private final int quantity;

    public OrdersDetailEntity(Long id, Long orders_id, Long product_id, int quantity) {
        this.id = id;
        this.ordersId = orders_id;
        this.productId = product_id;
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
