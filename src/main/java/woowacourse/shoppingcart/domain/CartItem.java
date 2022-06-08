package woowacourse.shoppingcart.domain;

public class CartItem {
    private Long id;
    private Long customerId;
    private Long productId;
    private int quantity;

    public CartItem(Long id, Long customerId, Long productId, int quantity) {
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

    public boolean matchId(Long id) {
        return this.productId.equals(id);
    }
}
