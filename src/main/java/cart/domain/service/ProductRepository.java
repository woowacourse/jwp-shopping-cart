package cart.domain.service;

import cart.domain.product.Product;

import java.util.List;

public interface ProductRepository {
    Long save(Product product);

    List<Product> findAll();

    int update(Product product);

    void deleteById(Long id);
}
