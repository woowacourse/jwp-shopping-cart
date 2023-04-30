package cart.dao;

import cart.controller.dto.CartRequest;
import cart.dao.entity.CartEntity;
import cart.dao.entity.ProductEntity;
import java.util.List;
import java.util.Optional;

public interface CartDao {

    long add(CartRequest request);

    List<ProductEntity> findByMeberId(Long memberId);

    Optional<CartEntity> findById(Long id);

    int deleteById(Long id);
}
