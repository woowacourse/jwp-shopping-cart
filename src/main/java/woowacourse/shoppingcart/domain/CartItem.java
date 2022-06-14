package woowacourse.shoppingcart.domain;

public class CartItem {

    private final Long id;
    private final Long customerId;
    private final Long productId;
    private final Amount count;

    public CartItem(Long id, Long customerId, Long productId, int count) {
        this(id, customerId, productId, new Amount(count));
    }

    public CartItem(Long id, Long customerId, Long productId, Amount count) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.count = count;
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

    public int getCount() {
        return count.getValue();
    }
}
