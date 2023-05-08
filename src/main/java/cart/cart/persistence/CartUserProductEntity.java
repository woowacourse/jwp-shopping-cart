package cart.cart.persistence;

import java.util.Objects;

public class CartUserProductEntity {
    private final Long id;
    private final Long cartUserId;
    private final Long productId;

    public CartUserProductEntity(final Long id, final Long cartUserId, final Long productId) {
        this.id = id;
        this.cartUserId = cartUserId;
        this.productId = productId;
    }

    public CartUserProductEntity(final Long cartUserId, final Long productId) {
        this.id = null;
        this.cartUserId = cartUserId;
        this.productId = productId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CartUserProductEntity that = (CartUserProductEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public Long getId() {
        return id;
    }

    public Long getCartUserId() {
        return cartUserId;
    }

    public Long getProductId() {
        return productId;
    }
}
