package cart.entity;

public class CartEntity {

    private final Long memberId;
    private final Long productId;

    private CartEntity(final Long memberId, final Long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public static class Builder {
        private Long memberId;
        private Long productId;

        public Builder memberId(Long memberId) {
            this.memberId = memberId;
            return this;
        }

        public Builder productId(Long productId) {
            this.productId = productId;
            return this;
        }

        public CartEntity build() {
            return new CartEntity(memberId, productId);
        }
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }
}
