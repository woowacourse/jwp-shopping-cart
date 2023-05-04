package cart.dao.entity;

public class CartEntity {

    private final Long productId;
    private final Long memberId;

    private CartEntity(final Long memberId, final Long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public static class Builder {

        private Long productId;
        private Long memberId;

        public Builder productId(final Long productId) {
            this.productId = productId;
            return this;
        }

        public Builder memberId(final Long memberId) {
            this.memberId = memberId;
            return this;
        }

        public CartEntity build() {
            return new CartEntity(memberId, productId);
        }
    }
}
