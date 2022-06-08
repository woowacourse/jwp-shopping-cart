package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.OrderDetail;

public class OrderDetailResponse {

    private Long productId;
    private int quantity;
    private int price;
    private String name;
    private String imageUrl;

    private OrderDetailResponse() {
    }

    public OrderDetailResponse(final Long productId, final int price, final String name,
                               final String imageUrl, final int quantity) {
        this.productId = productId;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public static OrderDetailResponse from(OrderDetail orderDetail) {
        return new OrderDetailResponse(orderDetail.getProductId(), orderDetail.getPrice(), orderDetail.getName(),
                orderDetail.getImageUrl(), orderDetail.getQuantity());
    }

    public Long getProductId() {
        return productId;
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

    public int getQuantity() {
        return quantity;
    }
}
