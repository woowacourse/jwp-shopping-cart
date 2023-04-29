package cart.dto;

import cart.domain.Product;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsResponse {

    private final List<ProductResponse> products;

    public ProductsResponse(List<ProductResponse> products) {
        this.products = products;
    }

    public static ProductsResponse of(List<Product> products) {
        List<ProductResponse> responses = products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
        return new ProductsResponse(responses);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductsResponse that = (ProductsResponse) o;

        return products != null ? products.equals(that.products) : that.products == null;
    }

    @Override
    public int hashCode() {
        return products != null ? products.hashCode() : 0;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }
}
