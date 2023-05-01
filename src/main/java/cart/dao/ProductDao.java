package cart.dao;

import java.util.List;

public interface ProductDao {
    Integer insert(String name, String image, Long price);

    void update(Integer id, String name, String image, Long price);

    void deleteById(Integer id);

    ProductEntity select(Integer id);

    List<ProductEntity> findAll();
}
