package cart.dao;

import cart.entity.CartEntity;
import java.util.List;
import java.util.Optional;

public interface CartDao {

  Long save(CartEntity cartEntity);

  List<CartEntity> findCartByMemberId(long memberId);

  void deleteByMemberIdAndProductId(long memberId, long productId);

  Optional<CartEntity> findCartByMemberIdAndProductId(long memberId, long productId);

  void addCartCount(int count, long memberId, long productId);
}
