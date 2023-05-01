package cart.dao;

import cart.entity.ProductCart;
import java.util.Optional;

public interface ProductCartDao {

    ProductCart save(ProductCart productCart);

    Optional<ProductCart> findById(Long id);
}
