package woowacourse.shoppingcart.domain;

public class CartItem {

    private final Id id;
    private final Id productId;
    private Quantity quantity;

    public CartItem(final Long id, final Long productId, final int quantity) {
        this.id = new Id(id);
        this.productId = new Id(productId);
        this.quantity = new Quantity(quantity);
    }

    public boolean hasSameProductId(final Long productId) {
        return this.productId.equals(new Id(productId));
    }

    public void plusQuantity() {
        this.quantity = this.quantity.plus();
    }

    public Long getId() {
        return id.getValue();
    }

    public Long getProductId() {
        return productId.getValue();
    }

    public int getQuantity() {
        return quantity.getValue();
    }
}
