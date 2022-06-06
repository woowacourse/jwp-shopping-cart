package woowacourse.shoppingcart.ui.dto;

import java.util.List;
import woowacourse.shoppingcart.application.dto.ProductResponse;

public class ProductsResponse {

    private final List<ProductResponse> products;

    public ProductsResponse(List<ProductResponse> products) {
        this.products = products;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }
}
