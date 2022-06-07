package woowacourse.shoppingcart.domain;

public class OrderDetail {

    private Long id;
    private Long orderId;
    private Long productId;
    private int quantity;

    public OrderDetail(final Long orderId, final Long productId, final int quantity) {
        this(null, orderId, productId, quantity);
    }

    public OrderDetail(final Long id, final Long orderId, final Long productId, final int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
