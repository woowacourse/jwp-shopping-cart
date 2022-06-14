package woowacourse.shoppingcart.domain.order;

public class OrderDetail {
    private Long id;
    private Long ordersId;
    private Long productId;
    private int quantity;

    public OrderDetail() {
    }

    public OrderDetail(Long id, Long ordersId, Long productId, int quantity) {
        this.id = id;
        this.ordersId = ordersId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderDetail(Long ordersId, Long productId, int quantity) {
        this(null, ordersId, productId, quantity);
    }

    public Long getId() {
        return id;
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
