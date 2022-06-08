package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.order.OrderDetail;

public class OrderDetailResponse {

    private ProductResponse product;
    private int quantity;

    private OrderDetailResponse() {
    }

    public OrderDetailResponse(ProductResponse product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public static OrderDetailResponse from(final OrderDetail orderDetail) {
        return new OrderDetailResponse(ProductResponse.from(orderDetail.getProduct()),
                orderDetail.getQuantity());
    }

    public ProductResponse getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
