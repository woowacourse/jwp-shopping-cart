package cart.entity;

import java.util.List;

public interface ProductRepository {
    Long save(Product product);

    Product findById(Long id);

    List<Product> findAll();

    void updateById(Product product, Long id);

    void deleteById(Long id);
}
