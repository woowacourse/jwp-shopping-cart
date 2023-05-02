package cart.fixture;

import cart.entity.CartEntity;

public class CartFixtures {

    /**
     * DUMMY_DATA
     */
    public static final Long DUMMY_MEMBER_ID = 1L;
    public static final Long DUMMY_PRODUCT_ID = 1L;

    /**
     * DUMMY_ENTITY
     */
    public static final CartEntity INSERT_CART_ENTITY =
            new CartEntity.Builder()
                    .memberId(DUMMY_MEMBER_ID)
                    .productId(DUMMY_PRODUCT_ID)
                    .build();
}
