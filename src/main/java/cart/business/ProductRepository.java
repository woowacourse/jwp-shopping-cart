package cart.business;

import cart.business.domain.Product;

import java.util.List;

public interface ProductRepository {

    Integer insert(Product product);

    List<Product> findAll();

    Product update(Product product);

    Product remove(Integer productId);

    Boolean findSameProductExist(Product product);
}
