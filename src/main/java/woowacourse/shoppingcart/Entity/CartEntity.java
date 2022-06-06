package woowacourse.shoppingcart.Entity;

public class CartEntity {
    private Long id;
    private Long customerId;
    private Long productId;
    private int quantity;

    public CartEntity() {
    }

    public CartEntity(Long customerId, Long productId, int quantity) {
        this(null, customerId, productId, quantity);
    }

    public CartEntity(Long id, Long customerId, Long productId, int quantity) {
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
