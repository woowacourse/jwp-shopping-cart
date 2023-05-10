package cart.fixture;

import static cart.fixture.MemberFixture.MEMBER1;
import static cart.fixture.ProductFixture.PRODUCT1;

import cart.domain.cartitem.CartItem;

public class CartItemFixture {

    public static final CartItem CART_ITEM_MEMBER1_PRODUCT1 = new CartItem(1L, MEMBER1.getId(), PRODUCT1.getId());
}
