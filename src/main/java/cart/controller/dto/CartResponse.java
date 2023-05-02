package cart.controller.dto;

import java.util.List;

public class CartResponse {
    private final List<ProductResponse> products;

    public CartResponse(List<ProductResponse> products) {
        this.products = products;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }
}
