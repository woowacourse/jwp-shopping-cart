package cart.dao.entity;

public class CartEntity {

    private final Long id;
    private final Long memberId;
    private final Long productId;

    private CartEntity(Long id, final Long memberId, final Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CartEntity{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", productId=" + productId +
                '}';
    }

    public static class Builder {

        private Long id;
        private Long productId;

        private Long memberId;

        public Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public Builder productId(final Long productId) {
            this.productId = productId;
            return this;
        }

        public Builder memberId(final Long memberId) {
            this.memberId = memberId;
            return this;
        }

        public CartEntity build() {
            return new CartEntity(id, memberId, productId);
        }

    }
}
