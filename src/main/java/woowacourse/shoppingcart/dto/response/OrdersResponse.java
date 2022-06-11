package woowacourse.shoppingcart.dto.response;

import java.util.List;

public class OrdersResponse {
    private List<OrderProductResponse> products;
    private Integer totalPrice;

    public OrdersResponse() {
    }

    public OrdersResponse(List<OrderProductResponse> products) {
        this.products = products;
        this.totalPrice = products.stream()
                .mapToInt(orderProduct -> orderProduct.getProduct().getPrice())
                .sum();
    }

    public List<OrderProductResponse> getProducts() {
        return products;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "products=" + products +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
