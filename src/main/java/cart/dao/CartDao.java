package cart.dao;

import java.util.List;

public interface CartDao {

    void insertCart(final CartEntity cartEntity);

    List<CartEntity> findCartByMemberId(final int id);

    void deleteById(final int cartId, final int memberId);

}
