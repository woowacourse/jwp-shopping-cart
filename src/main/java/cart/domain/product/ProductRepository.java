package cart.domain.product;

import java.util.List;

public interface ProductRepository {
    Long save(Product product);

    Product findById(Long id);

    List<Product> findAll();

    int update(Product product);

    void deleteById(Long id);
}
