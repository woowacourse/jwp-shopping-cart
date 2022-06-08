package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Product;

public class ProductsResponse {

    private final List<ProductResponse> productList;

    public ProductsResponse(List<Product> productList) {
        this.productList = productList.stream()
            .map(ProductResponse::new)
            .collect(Collectors.toUnmodifiableList());
    }

    public List<ProductResponse> getProductList() {
        return productList;
    }
}
