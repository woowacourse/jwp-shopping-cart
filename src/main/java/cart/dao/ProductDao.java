package cart.dao;

import java.util.List;

import cart.entity.ProductEntity;

public interface ProductDao {

    int insertProduct(ProductEntity productEntity);

    List<ProductEntity> selectAllProducts();

    void updateProduct(ProductEntity productEntity);

    void deleteProduct(int productId);
}
