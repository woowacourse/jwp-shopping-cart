package cart.domain.product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();

    Long save(Product product);

    void deleteById(Long id);
}
