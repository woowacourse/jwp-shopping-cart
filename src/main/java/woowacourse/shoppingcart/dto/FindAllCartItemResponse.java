package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.CartItems;
import woowacourse.shoppingcart.domain.Products;

public class FindAllCartItemResponse {

    private final List<FindCartItemResponse> products;

    public FindAllCartItemResponse(CartItems cartItems, Products products) {
        this.products = cartItems.getCartItems().stream()
                .map(it -> new FindCartItemResponse(it, products.findById(it.getProductId())))
                .collect(Collectors.toList());
    }

    public List<FindCartItemResponse> getProducts() {
        return products;
    }
}
