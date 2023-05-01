package cart.dao;

import java.util.List;

public interface ProductDao {
    Integer insert(ProductDto productEntity);

    void update(Integer id, ProductDto productDto);

    void deleteById(Integer id);

    ProductEntity select(Integer id);

    List<ProductEntity> findAll();
}
