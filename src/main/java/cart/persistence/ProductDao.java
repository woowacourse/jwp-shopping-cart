package cart.persistence;

import cart.domain.Product;

public interface ProductDao {

    void create(Product product);
}
