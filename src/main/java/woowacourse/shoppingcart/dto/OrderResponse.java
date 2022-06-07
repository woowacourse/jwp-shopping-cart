package woowacourse.shoppingcart.dto;

import java.util.List;

public class OrderResponse {
    private List<CartItemResponse> products;
    private int totalPrice;

    public OrderResponse() {
    }

    public OrderResponse(List<CartItemResponse> products, int totalPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public List<CartItemResponse> getProducts() {
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
