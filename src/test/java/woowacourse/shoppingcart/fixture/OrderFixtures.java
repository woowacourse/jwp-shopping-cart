package woowacourse.shoppingcart.fixture;

import static woowacourse.shoppingcart.fixture.CartItemFixtures.CART_REQUEST_1;
import static woowacourse.shoppingcart.fixture.CartItemFixtures.CART_REQUEST_2;

import java.util.List;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.OrderRequest;

public class OrderFixtures {
    public static final OrderRequest ORDER_REQUEST_1 = new OrderRequest(List.of(CART_REQUEST_1, CART_REQUEST_2));

    public static final OrderRequest ORDER_REQUEST_INVALID_PRODUCT = new OrderRequest(List.of(new CartRequest(999999L, 3), CART_REQUEST_2));
}
