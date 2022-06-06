package woowacourse.shoppingcart.domain;

public class OrderDetail {

    private Long id;
    private Long orderId;
    private Product product;
    private int quantity;

    public OrderDetail(final Long orderId, final Product product, final int quantity) {
        this(null, orderId, product, quantity);
    }

    public OrderDetail(final Long id, final Long orderId, final Product product, final int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
