package woowacourse.shoppingcart.domain;

public class OrderDetail {

    private final int quantity;
    private final Long orderId;
    private final Product product;

    public OrderDetail(final int quantity, final Long orderId, final Product product) {
        this.quantity = quantity;
        this.orderId = orderId;
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Product getProduct() {
        return product;
    }
}
