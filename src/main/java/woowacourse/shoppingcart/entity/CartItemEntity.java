package woowacourse.shoppingcart.entity;

public class CartItemEntity {
    private final Long id;
    private final Long customerId;
    private final Long productId;
    private final Integer quantity;

    public CartItemEntity(Long id, Long customerId, Long productId, Integer quantity) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "CartItemEntity{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
