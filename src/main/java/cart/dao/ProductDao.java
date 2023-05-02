package cart.dao;

import cart.entity.ProductEntity;

import java.util.List;

public interface ProductDao {
    int insert(ProductEntity product);

    List<ProductEntity> selectAll();

    int update(ProductEntity product);

    int delete(int productId);
}
