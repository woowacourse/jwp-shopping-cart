package cart.carts.domain;

import java.util.Objects;

public class CartItem {
    
    private final long id;
    
    private final long productId;
    
    public CartItem(final long id, final long productId) {
        this.id = id;
        this.productId = productId;
    }
    
    public long getId() {
        return this.id;
    }
    
    public long getProductId() {
        return this.productId;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.productId);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final CartItem cartItem = (CartItem) o;
        return this.id == cartItem.id && this.productId == cartItem.productId;
    }
}
