package woowacourse.shoppingcart.dto.product;

import woowacourse.shoppingcart.domain.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductListResponse {
    private List<ProductResponse> productList;

    public ProductListResponse() {
    }

    public ProductListResponse(List<ProductResponse> productList) {
        this.productList = productList;
    }

    public static ProductListResponse from(List<Product> products) {
        List<ProductResponse> productResponses = products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        return new ProductListResponse(productResponses);
    }

    public List<ProductResponse> getProductList() {
        return productList;
    }
}
