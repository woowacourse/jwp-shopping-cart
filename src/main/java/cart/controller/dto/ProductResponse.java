package cart.controller.dto;

import cart.domain.Product;

import java.util.List;

public class ProductResponse {

    private final List<Product> products;

    public ProductResponse(final List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }
}
