package woowacourse.shoppingcart.cart.domain;

public class Cart {

    private Long id;
    private Long customerId;
    private Long productId;
    private Quantity quantity;

    public Cart() {
    }

    public Cart(final Long id, final Long customerId, final Long productId, final Long quantity) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = new Quantity(quantity);
    }

    public Cart(final Long customerId, final Long productId, final Long quantity) {
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

    public Long getQuantity() {
        return quantity.get();
    }
}
