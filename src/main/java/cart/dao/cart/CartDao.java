package cart.dao.cart;

import cart.domain.Cart;
import cart.dto.CartDto;

import java.util.List;

public interface CartDao {

    List<Cart> findAll(Long memberId);

    void save(CartDto cartDto);

    void delete(CartDto cartDto);
}

