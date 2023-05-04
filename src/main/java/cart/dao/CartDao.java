package cart.dao;

import cart.controller.dto.CartResponse;
import cart.entity.CartEntity;
import java.util.List;

public interface CartDao {
    Long save(CartEntity cartEntity);

    List<CartResponse> findProductsByMemberId(Long id);

    void deleteById(Long cartId);
}
