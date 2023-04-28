package cart.domain.product.repository;

import cart.domain.product.entity.Product;

public interface ProductRepository {

    Product save(final Product product);
}
