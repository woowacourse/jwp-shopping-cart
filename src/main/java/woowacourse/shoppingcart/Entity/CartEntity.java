package woowacourse.shoppingcart.Entity;

public class CartEntity {
    private final Long id;
    private final Long customerId;
    private final Long productId;
    private final int quantity;

    public CartEntity(Long customerId, Long productId, int quantity) {
        this(null, customerId, productId, quantity);
    }

    public CartEntity(Long id, Long customerId, Long productId, int quantity) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }
    public CartEntity plusQuantity() {
        return new CartEntity(id,customerId,productId,quantity+1);
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
