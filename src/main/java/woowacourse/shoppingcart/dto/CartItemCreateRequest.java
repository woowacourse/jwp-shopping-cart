package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;

public class CartItemCreateRequest {

    @NotNull
    private Long productId;

    private CartItemCreateRequest() {
    }

    public CartItemCreateRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public CartItem toCartItem(final Product product) {
        return new CartItem(product);
    }
}
