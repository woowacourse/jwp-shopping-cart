package woowacourse.shoppingcart.domain;

public class Cart {

    private Long id;
    private Long customerId;
    private Long productId;
    private long quantity;
    private boolean checked;

    public Cart() {
    }

    public Cart(final Long id, final Long customerId, final Long productId, final long quantity,
                final boolean checked) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.checked = checked;
    }

    public void update(long quantity, boolean checked) {
        this.quantity = quantity;
        this.checked = checked;
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

    public long getQuantity() {
        return quantity;
    }

    public boolean isChecked() {
        return checked;
    }
}
