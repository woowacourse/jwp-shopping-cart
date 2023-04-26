package cart.dao;

import cart.entity.ProductEntity;

import java.util.List;

public interface ProductDao {

    int insertProduct(ProductEntity productEntity);

    List<ProductEntity> selectAllProducts();

    void updateProduct(ProductEntity productEntity);

    void deleteProduct(int productId);

    void deleteAllProduct();
}
