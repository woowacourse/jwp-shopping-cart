package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Cart;

public class CartResponses {

    private List<CartResponse> products;

    public CartResponses() {
    }

    public CartResponses(List<CartResponse> products) {
        this.products = products;
    }

    public static CartResponses from(List<Cart> carts) {
        List<CartResponse> cartResponses = carts.stream()
                .map(CartResponse::from)
                .collect(Collectors.toList());
        return new CartResponses(cartResponses);
    }

    public List<CartResponse> getProducts() {
        return products;
    }
}
