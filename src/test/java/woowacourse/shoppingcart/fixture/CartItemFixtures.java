package woowacourse.shoppingcart.fixture;

import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_1;

import woowacourse.shoppingcart.dto.CartRequest;

public class CartItemFixtures {
    public static final CartRequest CART_REQUEST_1 = new CartRequest(PRODUCT_1.getId(), 5);
}
