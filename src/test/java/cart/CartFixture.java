package cart;

import static cart.MemberFixture.TEST_MEMBER1;
import static cart.ProductFixture.PRODUCT_ENTITY1;

public class CartFixture {

    public static final CartRecordData TEST_CART_RECORD = new CartRecordData(1L, TEST_MEMBER1.getId(),
            PRODUCT_ENTITY1.getId());

    public static class CartRecordData {

        private final Long id;
        private final Long memberId;
        private final Long productId;

        public CartRecordData(final Long id, final Long memberId, final Long productId) {
            this.id = id;
            this.memberId = memberId;
            this.productId = productId;
        }

        public Long getId() {
            return id;
        }

        public Long getMemberId() {
            return memberId;
        }

        public Long getProductId() {
            return productId;
        }
    }
}
