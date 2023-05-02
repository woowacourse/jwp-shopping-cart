package cart.dao;

import cart.entity.CartEntity;
import java.util.List;

public interface CartDao {

  Long save(CartEntity cartEntity);

  List<CartEntity> findCartByMemberId(long memberId);
}
