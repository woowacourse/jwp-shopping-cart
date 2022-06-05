package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.OrderDetail;

public class OrderDetailResponse {

    private Long id;
    private Long productId;
    private String name;
    private int price;
    private int quantity;
    private String imageURL;

    private OrderDetailResponse() {
    }

    public OrderDetailResponse(final Long id, final Long productId, final String name, final int price,
                               final int quantity, final String imageURL) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageURL = imageURL;
    }

    public static OrderDetailResponse from(final OrderDetail orderDetail) {
        return new OrderDetailResponse(
                orderDetail.getId(),
                orderDetail.getProductId(),
                orderDetail.getName(),
                orderDetail.getPrice(),
                orderDetail.getQuantity(),
                orderDetail.getImageUrl()
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

    public int getQuantity() {
        return quantity;
    }

    public String getImageURL() {
        return imageURL;
    }
}
