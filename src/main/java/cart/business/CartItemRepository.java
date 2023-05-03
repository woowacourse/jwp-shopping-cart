package cart.business;

import cart.business.domain.cart.CartItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository {

    Integer save(CartItem cartItem);

    void remove(Integer cartItemId); // TODO : 삭제한 도메인 객체 반환

    List<CartItem> findAllByMemberId(Integer memberId);
}
