package cart.repository.entity;

import java.util.Objects;

public class CartEntity {

    private final Long id;
    private final Long memberId;
    private final Long productId;

    public CartEntity(final Long id, final Long memberId, final Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public CartEntity(final Long memberId, final Long productId) {
        this.id = null;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CartEntity that = (CartEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(memberId, that.memberId)
                && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, memberId, productId);
    }
}
