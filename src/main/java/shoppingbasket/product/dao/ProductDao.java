package shoppingbasket.product.dao;

import shoppingbasket.product.entity.ProductEntity;
import java.util.List;

public interface ProductDao {
    ProductEntity insert(ProductEntity product);

    List<ProductEntity> selectAll();

    ProductEntity findById(int productId);

    ProductEntity update(ProductEntity product);

    int delete(int productId);
}
