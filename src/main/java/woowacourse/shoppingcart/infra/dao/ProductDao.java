package woowacourse.shoppingcart.infra.dao;

import java.util.List;
import java.util.Optional;
import woowacourse.shoppingcart.infra.dao.entity.ProductEntity;

public interface ProductDao {
    ProductEntity save(ProductEntity productEntity);

    List<ProductEntity> findAllWithPage(int page, int size);

    Optional<ProductEntity> findById(long id);

    void deleteById(long id);

    long countAll();
}
