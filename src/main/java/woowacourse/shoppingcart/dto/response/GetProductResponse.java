package woowacourse.shoppingcart.dto.response;

import java.util.List;
import woowacourse.shoppingcart.domain.Product;

public class GetProductResponse {

    private List<Product> products;

    public GetProductResponse() {
    }

    public GetProductResponse(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
