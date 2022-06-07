package woowacourse.shoppingcart.entity;

public class OrdersDetailEntity {
    private final Long id;
    private final Long orders_id;
    private final Long product_id;
    private final int quantity;

    public OrdersDetailEntity(Long id, Long orders_id, Long product_id, int quantity) {
        this.id = id;
        this.orders_id = orders_id;
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getOrders_id() {
        return orders_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public int getQuantity() {
        return quantity;
    }
}
