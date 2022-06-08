package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.order.OrderDetail;

public class OrderDetailResponse {

    private final Long id;
    private final String name;
    private final int cost;
    private final String imageUrl;
    private final int quantity;

    public OrderDetailResponse(Long id, String name, int cost, String imageUrl, int quantity) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public static OrderDetailResponse from(OrderDetail orderDetail) {
        return new OrderDetailResponse(
                orderDetail.getId(),
                orderDetail.getProduct().getName().getValue(),
                orderDetail.calculateCost(),
                orderDetail.getProduct().getImageUrl().getValue(),
                orderDetail.getQuantity()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }
}
