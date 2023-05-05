package cart.domain.cartitem;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class CartItem {

    private final Long id;
    private final Long memberId;
    private final Long productId;

    public CartItem(final Long memberId, final Long product_id) {
        this(null, memberId, product_id);
    }
}
