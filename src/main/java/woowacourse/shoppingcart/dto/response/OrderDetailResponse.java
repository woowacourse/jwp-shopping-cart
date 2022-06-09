package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.OrderDetail;

public class OrderDetailResponse {

    private Long productId;
    private int quantity;
    private int price;
    private String name;
    private String imageUrl;

    private OrderDetailResponse() {
    }

    public OrderDetailResponse(final OrderDetail orderDetail) {
        this.productId = orderDetail.getProductId();
        this.price = orderDetail.getPrice();
        this.name = orderDetail.getName();
        this.imageUrl = orderDetail.getImageUrl();
        this.quantity = orderDetail.getQuantity();
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
