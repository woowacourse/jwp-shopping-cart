package woowacourse.shoppingcart.order.dto;

import java.util.List;

public class OrderResponse {
    private Long id;
    private List<OrderDetailResponse> orderedProducts;

    public OrderResponse(Long id, List<OrderDetailResponse> orderedProducts) {
        this.id = id;
        this.orderedProducts = orderedProducts;
    }

    public Long getId() {
        return id;
    }

    public List<OrderDetailResponse> getOrderedProducts() {
        return orderedProducts;
    }
}
