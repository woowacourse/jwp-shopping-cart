package cart.repository.cartproduct;

import cart.domain.cartproduct.CartProduct;
import cart.domain.cartproduct.CartProductId;

import java.util.Optional;

public interface CartProductRepository {
    CartProductId save(final CartProduct cartProduct);

    Optional<CartProduct> findByCartProductId(final CartProductId cartProductId);
}
