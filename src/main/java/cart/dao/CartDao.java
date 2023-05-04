package cart.dao;

import cart.entity.CartEntity;
import cart.entity.ProductEntity;
import java.util.List;

public interface CartDao {
    Long save(CartEntity cartEntity);

    List<ProductEntity> findProductsByMemberId(Long id);
}
