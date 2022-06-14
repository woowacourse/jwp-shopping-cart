package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;

public class OrderDetailResponse {

    private final long id;
    private final String name;
    private final int cost;
    private final int quantity;
    private final String imageUrl;

    public OrderDetailResponse(long id, String name, int cost, int quantity, String imageUrl) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    public static OrderDetailResponse of(OrderDetail orderDetail) {
        final Product product = orderDetail.getProduct();
        return new OrderDetailResponse(product.getId(), product.getName().getValue(), product.getPrice() * orderDetail.getQuantity(), orderDetail.getQuantity(), product.getImageUrl().getValue());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
