package cart.cart_product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CartProduct {

    private Long id;
    private final Long cartId;
    private final Long productId;

    public static CartProduct of(final Long id, final Long cartId, final Long productId) {
        return new CartProduct(id, cartId, productId);
    }
}
