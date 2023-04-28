package cart.dao;

import cart.entity.ProductEntity;
import cart.repository.ProductDto;

import java.util.List;

public interface ProductDao {
    Integer insert(ProductDto productEntity);

    void update(Integer id, ProductDto productDto);

    void deleteById(Integer id);

    ProductEntity select(Integer id);

    List<ProductEntity> findAll();
}
