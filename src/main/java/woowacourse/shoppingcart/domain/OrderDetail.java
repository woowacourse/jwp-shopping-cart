package woowacourse.shoppingcart.domain;

public class OrderDetail {

    private final Id productId;
    private final int quantity;

    public OrderDetail(final Long productId, final int quantity) {
        this.productId = new Id(productId);
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId.getValue();
    }

    public int getQuantity() {
        return quantity;
    }
}
