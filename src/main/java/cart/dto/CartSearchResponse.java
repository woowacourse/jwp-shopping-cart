package cart.dto;

import java.util.List;

public class CartSearchResponse {

    private final List<ProductDto> products;

    public CartSearchResponse(final List<ProductDto> products) {
        this.products = products;
    }

    public List<ProductDto> getProducts() {
        return products;
    }
}
