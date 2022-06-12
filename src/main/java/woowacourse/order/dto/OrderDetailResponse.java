package woowacourse.order.dto;

import woowacourse.order.domain.OrderDetail;

public class OrderDetailResponse {

    private Long id;
    private Long productId;
    private String name;
    private int price;
    private String imageURL;
    private int quantity;

    private OrderDetailResponse() {
    }

    public OrderDetailResponse(final Long id, final Long productId, final String name, final int price,
        final String imageURL, final int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
        this.quantity = quantity;
    }

    public static OrderDetailResponse from(final OrderDetail orderDetail) {
        return new OrderDetailResponse(
            orderDetail.getId(), orderDetail.getProductId(), orderDetail.getName(),
            orderDetail.getPrice().getValue(), orderDetail.getImageURL(), orderDetail.getQuantity().getValue()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getQuantity() {
        return quantity;
    }
}
