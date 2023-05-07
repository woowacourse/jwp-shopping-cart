package cart.persistence.entity;

import java.util.Objects;

public class CartProductEntity {

    private final Long id;
    private final Long memberId;
    private final Long productId;

    private CartProductEntity(Long id, Long memberId, Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public static CartProductEntity create(Long id, Long memberId, Long productId) {
        return new CartProductEntity(id, memberId, productId);
    }

    public static CartProductEntity createToSave(Long memberId, Long productId) {
        return new CartProductEntity(null, memberId, productId);
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartProductEntity that = (CartProductEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
