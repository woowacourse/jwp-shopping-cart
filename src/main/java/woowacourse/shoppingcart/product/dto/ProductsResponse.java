package woowacourse.shoppingcart.product.dto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.product.domain.Product;

public class ProductsResponse {

    private final List<ProductResponse> productList;

    private ProductsResponse(final List<ProductResponse> productList) {
        this.productList = productList;
    }

    public static ProductsResponse from(final List<Product> products) {
        final List<ProductResponse> responses = products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        return new ProductsResponse(responses);
    }

    public List<ProductResponse> getProductList() {
        return productList;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductsResponse that = (ProductsResponse) o;
        return Objects.equals(productList, that.productList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productList);
    }
}
