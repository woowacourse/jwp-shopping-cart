package woowacourse.shoppingcart.fixture;

import woowacourse.shoppingcart.dto.CartRequest;

public class CartItemFixtures {
    public static final CartRequest CART_REQUEST_1 = new CartRequest(ProductFixtures.PRODUCT_1.getId(), 5);
    public static final CartRequest CART_REQUEST_2 = new CartRequest(ProductFixtures.PRODUCT_2.getId(), 100);
}
