package woowacourse.shoppingcart.entity;

import java.util.Objects;

public class OrderDetailEntity {

    private final Long id;
    private final Long orderId;
    private final Long productId;
    private final int quantity;

    public OrderDetailEntity(Long id, Long orderId, Long productId, int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderDetailEntity(Long orderId, Long productId, int quantity) {
        this(null, orderId, productId, quantity);
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderDetailEntity that = (OrderDetailEntity) o;
        return quantity == that.quantity
                && Objects.equals(id, that.id)
                && Objects.equals(orderId, that.orderId)
                && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, productId, quantity);
    }
}
