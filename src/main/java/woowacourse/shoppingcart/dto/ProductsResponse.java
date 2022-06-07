package woowacourse.shoppingcart.dto;

import java.util.List;

public class ProductsResponse {

    private final List<ProductResponse> productList;

    public ProductsResponse(List<ProductResponse> productList) {
        this.productList = productList;
    }

    public List<ProductResponse> getProductList() {
        return productList;
    }
}
