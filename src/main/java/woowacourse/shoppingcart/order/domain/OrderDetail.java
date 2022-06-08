package woowacourse.shoppingcart.order.domain;

import woowacourse.shoppingcart.product.domain.Product;

public class OrderDetail {

    private Long orderId;
    private Long productId;
    private Long quantity;

    public OrderDetail() {
    }

    public OrderDetail(final Long orderId, final Long productId, final Long quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderDetail(final Long orderId, final Product product, final Long quantity) {
        this(orderId, product.getId(), quantity);
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }
}
