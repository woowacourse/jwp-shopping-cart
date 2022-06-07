package woowacourse.shoppingcart.domain;

public class OrderDetail {

    private final Long productId;
    private final int quantity;

    public OrderDetail(final Long productId, final int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
