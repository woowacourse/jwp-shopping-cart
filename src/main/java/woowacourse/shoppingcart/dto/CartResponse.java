package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

import java.util.List;
import java.util.stream.Collectors;

public class CartResponse {

    private final List<ProductResponse> cart;

    public CartResponse(List<ProductResponse> cart) {
        this.cart = cart;
    }

    public List<ProductResponse> getCart() {
        return cart;
    }

    public static CartResponse of(List<Product> products) {
        final List<ProductResponse> convertedProducts = products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
        return new CartResponse(convertedProducts);
    }
}
