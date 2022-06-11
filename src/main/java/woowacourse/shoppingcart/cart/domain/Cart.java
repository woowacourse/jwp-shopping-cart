package woowacourse.shoppingcart.cart.domain;

public class Cart {

    private static final long TEMPORARY_ID = 0;

    private long id;
    private long customerId;
    private long productId;
    private Quantity quantity;

    public Cart() {
    }

    public Cart(final long id, final long customerId, final long productId, final long quantity) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = new Quantity(quantity);
    }

    public Cart(final long customerId, final long productId, final long quantity) {
        this(TEMPORARY_ID, customerId, productId, quantity);
    }

    public long getId() {
        return id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public long getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity.get();
    }
}
