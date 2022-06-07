package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.order.OrderDetail;

public class OrderDetailResponse {

    private final Long id;
    private final Integer quantity;
    private final Long productId;
    private final String name;
    private final Integer price;
    private final String imageUrl;

    private OrderDetailResponse() {
        this(null, null, null, null, null, null);
    }

    public OrderDetailResponse(Long id, Integer quantity, Long productId, String name, Integer price, String imageUrl) {
        this.id = id;
        this.quantity = quantity;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static OrderDetailResponse from(OrderDetail orderDetail) {
        return new OrderDetailResponse(
            orderDetail.getId(),
            orderDetail.getQuantity(),
            orderDetail.getProductId(),
            orderDetail.getName(),
            orderDetail.getPrice(),
            orderDetail.getImageUrl()
        );
    }

    public Long getId() {
        return id;
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
