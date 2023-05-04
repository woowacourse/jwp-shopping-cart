package cart.dao.user.userproduct;

import java.util.Objects;

public class CartUserProductEntity {
    private final Long id;
    private final Long cartUserId;
    private final Long productId;

    public CartUserProductEntity(Long id, Long cartUserId, Long productId) {
        this.id = id;
        this.cartUserId = cartUserId;
        this.productId = productId;
    }

    public CartUserProductEntity(Long cartUserId, Long productId) {
        this.id = null;
        this.cartUserId = cartUserId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartUserProductEntity that = (CartUserProductEntity) o;
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
