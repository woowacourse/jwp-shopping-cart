package woowacourse.shoppingcart.dao.entity;

public class CartItemEntity {

    private final Long id;
    private final Long customerId;
    private final Long productId;
    private final int quantity;

    public CartItemEntity(Long id, Long customerId, Long productId, int quantity) {
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

    public int getQuantity() {
        return quantity;
    }
}
