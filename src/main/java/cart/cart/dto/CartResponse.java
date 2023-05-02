package cart.cart.dto;

import cart.cart.domain.Cart;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class CartResponse {
    private final Long id;
    private final Long memberId;
    private final Long productId;
    
    public CartResponse(final Long id, final Long memberId, final Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }
    
    public CartResponse(final Cart cart) {
        this.id = cart.getId();
        this.memberId = cart.getMemberId();
        this.productId = cart.getProductId();
    }
}
