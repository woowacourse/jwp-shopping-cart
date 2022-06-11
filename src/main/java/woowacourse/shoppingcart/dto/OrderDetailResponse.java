package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.OrderDetail;

public class OrderDetailResponse {

    private Long productId;
    private int quantity;
    private int price;
    private String name;
    private String imageUrl;

    private OrderDetailResponse(Long productId, int quantity, int price, String name, String imageUrl) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public static OrderDetailResponse from(OrderDetail orderDetail) {
        return new OrderDetailResponse(orderDetail.getProductId(), orderDetail.getQuantity(),
                orderDetail.getPrice(), orderDetail.getName(), orderDetail.getImageUrl());
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
