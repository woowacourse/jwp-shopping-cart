package woowacourse.shoppingcart.domain;

public class CartItem {

    private final Long id;
    private final Long productId;
    private int quantity;

    public CartItem(final Long id, final Long productId, final int quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }

    public boolean hasSameProductId(final Long productId) {
        return this.productId.equals(productId);
    }

    public void plusQuantity() {
        this.quantity++;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
