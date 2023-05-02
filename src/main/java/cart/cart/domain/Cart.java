package cart.cart.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode
public class Cart {
    private final Long id;
    private final Long memberId;
    private final Long productId;
    
    public Cart(final Long id, final Long memberId, final Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }
    
    public boolean isSame(final Long productId, final Long memberId) {
        return this.productId.equals(productId) && this.memberId.equals(memberId);
    }
}
