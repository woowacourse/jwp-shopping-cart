package cart.dao.entity;

public class CartEntity {

    private final int productId;
    private final int memberId;

    private CartEntity(final int memberId, final int productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public int getMemberId() {
        return memberId;
    }

    public static class Builder {

        private int productId;
        private int memberId;

        public Builder productId(final int productId) {
            this.productId = productId;
            return this;
        }

        public Builder memberId(final int memberId) {
            this.memberId = memberId;
            return this;
        }

        public CartEntity build() {
            return new CartEntity(memberId, productId);
        }
    }
}
