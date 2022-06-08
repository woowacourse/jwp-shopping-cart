package woowacourse.shoppingcart.ui.product.dto.response;

import java.util.List;

public class ProductsResponse {

    private int totalPrice;
    private List<ProductResponse> products;

    public ProductsResponse() {
    }

    public ProductsResponse(final int totalPrice, final List<ProductResponse> products) {
        this.totalPrice = totalPrice;
        this.products = products;
    }

    public static ProductsResponse from(final List<ProductResponse> products) {
        return new ProductsResponse(products.size(), products);
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }
}
