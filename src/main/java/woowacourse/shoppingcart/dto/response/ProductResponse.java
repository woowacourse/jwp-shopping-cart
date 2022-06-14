package woowacourse.shoppingcart.dto.response;

import java.util.List;
import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {

    private List<Product> products;

    public ProductResponse() {
    }

    public ProductResponse(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
