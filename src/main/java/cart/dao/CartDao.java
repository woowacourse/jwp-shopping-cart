package cart.dao;

import cart.controller.dto.CartRequest;
import cart.dao.entity.CartEntity;
import cart.dao.entity.ProductEntity;
import cart.domain.Member;
import java.util.List;
import java.util.Optional;

public interface CartDao {

    long add(CartRequest request);

    List<ProductEntity> findByMeber(Member member);

    Optional<CartEntity> findById(Long id);

    int deleteById(Long id);
}
