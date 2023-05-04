package cart.domain;

import java.util.List;

import cart.dto.ProductDto;

public interface ProductRepository {
    Long save(Product product);

    Product findById(Long id);

    List<Product> findAll();

    void updateById(Product product, Long id);

    void deleteById(Long id);
}
