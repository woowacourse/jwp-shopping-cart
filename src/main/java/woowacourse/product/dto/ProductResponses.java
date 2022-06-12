package woowacourse.product.dto;

import static woowacourse.product.dto.ProductResponse.*;

import java.util.List;
import java.util.stream.Collectors;

import woowacourse.product.domain.Product;

public class ProductResponses {

    private List<InnerProductResponse> products;

    private ProductResponses() {
    }

    public ProductResponses(final List<InnerProductResponse> products) {
        this.products = products;
    }

    public static ProductResponses from(final List<Product> products) {
        final List<InnerProductResponse> productDetailResponse = products.stream()
            .map(InnerProductResponse::from)
            .collect(Collectors.toList());
        return new ProductResponses(productDetailResponse);
    }

    public List<InnerProductResponse> getProducts() {
        return products;
    }
}
