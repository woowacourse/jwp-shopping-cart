package cart.dto;

import cart.domain.product.Product;
import java.util.List;
import java.util.stream.Collectors;

public class CartProductsResponse {

    private final List<CartProductResponse> products;

    public CartProductsResponse(final List<CartProductResponse> products) {
        this.products = products;
    }

    public static CartProductsResponse of(final List<Product> products) {
        List<CartProductResponse> responses = products.stream()
                .map(CartProductResponse::of)
                .collect(Collectors.toList());
        return new CartProductsResponse(responses);
    }

    public List<CartProductResponse> getProducts() {
        return products;
    }
}
