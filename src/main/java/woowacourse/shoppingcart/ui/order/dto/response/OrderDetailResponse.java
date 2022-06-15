package woowacourse.shoppingcart.ui.order.dto.response;

import woowacourse.shoppingcart.domain.OrderDetail;

public class OrderDetailResponse {

    private Long productId;
    private int quantity;
    private int price;
    private String name;
    private String imageUrl;

    public OrderDetailResponse() {
    }

    public OrderDetailResponse(final Long productId, final int quantity, final int price, final String name,
                               final String imageUrl) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public static OrderDetailResponse from(final OrderDetail orderDetail) {
        return new OrderDetailResponse(orderDetail.getProductId(), orderDetail.getQuantity(), orderDetail.getPrice(),
                orderDetail.getName(), orderDetail.getImageUrl());
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
