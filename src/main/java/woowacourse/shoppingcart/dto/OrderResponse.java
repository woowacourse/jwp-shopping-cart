package woowacourse.shoppingcart.dto;

import java.util.List;

public class OrderResponse {
    private List<OrderDetailResponse> products;
    private int totalPrice;

    public OrderResponse() {
    }

    public OrderResponse(List<OrderDetailResponse> products, int totalPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public List<OrderDetailResponse> getProducts() {
        return products;
    }

    public int getTotalPrice() {
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
