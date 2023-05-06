package cart.entity;

import java.util.Objects;

public class CartEntity {

    private final Long id;
    private final Long memberId;
    private final Long productId;

    public CartEntity(Long id, Long memberId, Long productId) {
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

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartEntity that = (CartEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        return "CartEntity{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", productId=" + productId +
                '}';
    }
}