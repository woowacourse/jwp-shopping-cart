package woowacourse.shoppingcart.dto.order;

import woowacourse.shoppingcart.domain.OrderDetail;

public class OrderDetailResponse {

    private long id;
    private long productId;
    private String name;
    private int price;
    private int quantity;
    private String imageURL;

    private OrderDetailResponse() {
    }

    public OrderDetailResponse(long id, long productId, String name, int price, int quantity, String imageURL) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageURL = imageURL;
    }

    public static OrderDetailResponse from(final OrderDetail orderDetail) {
        return new OrderDetailResponse(orderDetail.getId(), orderDetail.getProductId(), orderDetail.getName(),
                orderDetail.getPrice(), orderDetail.getQuantity(), orderDetail.getImageUrl());
    }

    public long getId() {
        return id;
    }

    public long getProductId() {
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
