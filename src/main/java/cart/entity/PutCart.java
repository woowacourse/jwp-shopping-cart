package cart.entity;

import java.util.Objects;

public class PutCart {

    private final Long memberId;
    private final Long productId;

    public PutCart(Long memberId, Long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PutCart putCart = (PutCart) o;
        return Objects.equals(memberId, putCart.memberId) && Objects.equals(productId, putCart.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, productId);
    }
}
