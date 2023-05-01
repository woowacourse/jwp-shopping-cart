package cart.entity;

import java.util.Objects;

public final class CartEntity {

    private Long id;
    private Long memberId;
    private Long productId;

    public CartEntity(final Long memberId, final Long productId) {
        this(null, memberId, productId);
    }

    public CartEntity(final Long id, final Long memberId, final Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public boolean isOwner(final MemberEntity member) {
        return memberId.equals(member.getId());
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartEntity that = (CartEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
