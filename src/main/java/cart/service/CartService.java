package cart.service;

import cart.dao.cart.CartDao;
import cart.domain.Cart;
import cart.dto.CartDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public void add(CartDto cartDto) {
        cartDao.save(cartDto);
    }

    public List<Cart> findAll(Long memberId) {
        return cartDao.findAll(memberId);
    }

    public void delete(CartDto cartDto) {
        cartDao.delete(cartDto);
    }

}
