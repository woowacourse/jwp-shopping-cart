package woowacourse.cartitem.domain;

public class CartItem {

    private Long id;
    private Long customerId;
    private Long productId;
    private Quantity quantity;

    public CartItem(final Long id, final Long customerId, final Long productId, final Quantity quantity) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public CartItem(final Long customerId, final Long productId, final Quantity quantity) {
        this(null, customerId, productId, quantity);
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

    public Quantity getQuantity() {
        return quantity;
    }
}
