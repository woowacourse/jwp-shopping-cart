package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;

public class OrderDetailResponse {

    private Long productId;
    private int quantity;
    private int price;
    private String name;
    private String imageUrl;

    private OrderDetailResponse() {
    }

    public OrderDetailResponse(final Long productId, final int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderDetailResponse(final Product product, final int quantity) {
        this(product.getId(), product.getPrice(), product.getName(), product.getImageUrl(), quantity);
    }

    public OrderDetailResponse(final Long productId, final int price, final String name,
            final String imageUrl, final int quantity) {
        this.productId = productId;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public static OrderDetailResponse of(OrderDetail orderDetail) {
        return new OrderDetailResponse(orderDetail.getProductId(),
                orderDetail.getPrice(),
                orderDetail.getName(),
                orderDetail.getImageUrl(),
                orderDetail.getQuantity());
    }

    public static List<OrderDetailResponse> of(List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .map(OrderDetailResponse::of)
                .collect(Collectors.toList());
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
