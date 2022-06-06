package woowacourse.shoppingcart.fixture;

import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_1;

import woowacourse.shoppingcart.dto.CartItemUpdateRequest;

public class CartItemFixtures {
    public static final CartItemUpdateRequest CART_ITEM_UPDATE_REQUEST_1 =
            new CartItemUpdateRequest(PRODUCT_1.getId(), 5);
}
