package woowacourse.shoppingcart.dto;

import java.util.List;

public class ProductResponses {
    private List<ProductResponse> products;

    public ProductResponses() {
    }

    public ProductResponses(List<ProductResponse> products) {
        this.products = products;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "ProductResponses{" +
                "products=" + products +
                '}';
    }
}
