package cart.cart_product.dao;

import cart.product.domain.Product;

import java.util.List;

public interface CartProductDao {

    void insert(final Long cartId, final Long productId);

    int countByCartIdAndProductId(final Long cartId, final Long productId);

    List<Product> findAllProductByCartId(final Long cartId);

    void deleteByCartIdAndProductId(final Long cartId, final Long productId);
}
