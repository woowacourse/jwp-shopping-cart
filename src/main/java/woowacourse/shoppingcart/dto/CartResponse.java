package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Cart;

import java.util.List;
import java.util.stream.Collectors;

public class CartResponse {

    private final List<ProductResponse> cart;

    public CartResponse(List<ProductResponse> cart) {
        this.cart = cart;
    }

    public static CartResponse of(Cart cart) {
        final List<ProductResponse> convertedProducts = cart.getProducts()
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
        return new CartResponse(convertedProducts);
    }

    public List<ProductResponse> getCart() {
        return cart;
    }
}
