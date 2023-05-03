package cart.persistence;

import cart.entity.Product;

import java.util.List;

public interface ProductRepository {

    Integer insert(Product product);

    List<Product> findAll();

    Integer update(Integer id, Product product);

    Integer remove(Integer id);

    void findSameProductExist(Product product);
}
