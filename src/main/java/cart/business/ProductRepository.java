package cart.business;

import cart.business.domain.Product;

import java.util.List;

public interface ProductRepository {

    Integer insert(Product product);

    List<Product> findAll();

    Integer update(Integer id, Product product);

    Integer remove(Integer id);

    Boolean findSameProductExist(Product product);
}
