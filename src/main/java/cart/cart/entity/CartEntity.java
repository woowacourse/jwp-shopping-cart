package cart.cart.entity;

import java.util.Objects;

public class CartEntity {

    private final Integer id;
    private final int memberId;
    private final int productId;

    public CartEntity(final int id, final int memberId, final int productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public Integer getId() {
        return id;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getProductId() {
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
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
