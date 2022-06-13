package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.CartItems;
import woowacourse.shoppingcart.domain.Products;

public class FindAllCartItemResponse {

    private final List<FindCartItemResponse> cartItems;

    public FindAllCartItemResponse(CartItems cartItems, Products products) {
        this.cartItems = cartItems.getCartItems().stream()
                .map(it -> new FindCartItemResponse(it, products.findById(it.getProductId())))
                .collect(Collectors.toList());
    }

    public List<FindCartItemResponse> getCartItems() {
        return cartItems;
    }
}
