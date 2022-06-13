package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.Products;

import java.util.List;

public class ProductsResponse {

    private List<Product> products;

    public ProductsResponse() {
    }

    public ProductsResponse(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }
}
