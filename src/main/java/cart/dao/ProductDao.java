package cart.dao;

import java.util.List;

public interface ProductDao {

    List<ProductEntity> findAll();

    void insert(ProductEntity productEntity);

}
