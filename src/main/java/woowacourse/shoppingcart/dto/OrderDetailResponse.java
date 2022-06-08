package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;

public class OrderDetailResponse {

    @JsonProperty(value = "orderDetailId")
    private Long id;
    private Long productId;
    private String name;
    private int totalPrice;
    private String imageUrl;
    private int quantity;

    private OrderDetailResponse() {

    }

    public OrderDetailResponse(final Long id, final Long productId, final String name, final int totalPrice,
                               final String imageUrl, final int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.totalPrice = totalPrice;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public static OrderDetailResponse from(final OrderDetail orderDetail, final Product product) {
        return new OrderDetailResponse(orderDetail.getId(),
                product.getId(),
                product.getName(),
                product.getPrice() * orderDetail.getQuantity(),
                product.getImageUrl(),
                orderDetail.getQuantity());
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

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }
}
