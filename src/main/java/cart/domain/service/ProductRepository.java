package cart.domain.service;

import cart.domain.product.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();

    Long save(Product product);

    void deleteById(Long id);

    int update(Product product);
}
