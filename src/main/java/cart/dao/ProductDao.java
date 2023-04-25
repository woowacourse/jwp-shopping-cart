package cart.dao;

import cart.entity.ProductEntity;

import java.util.List;

public interface ProductDao {

    void insertProduct(ProductEntity productEntity);

    List<ProductEntity> selectAllProducts();
}
