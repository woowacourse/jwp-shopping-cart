package woowacourse.shoppingcart.order.dto;

import woowacourse.shoppingcart.order.domain.OrderDetail;
import woowacourse.shoppingcart.product.domain.Product;

public class OrderDetailResponse {

    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final int quantity;

    private OrderDetailResponse(final Long id, final String name, final int price, final String imageUrl, final int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public static OrderDetailResponse from(final OrderDetail orderDetail) {
        final Product product = orderDetail.getProduct();
        return new OrderDetailResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                orderDetail.getQuantity()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }
}
