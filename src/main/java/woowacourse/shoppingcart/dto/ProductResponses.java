package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Products;

public class ProductResponses {

    private List<ProductResponse> products;

    public ProductResponses() {
    }

    public ProductResponses(List<ProductResponse> products) {
        this.products = products;
    }

    public static ProductResponses from(Products products) {
        List<ProductResponse> productResponses = products.getValue().stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        return new ProductResponses(productResponses);
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
