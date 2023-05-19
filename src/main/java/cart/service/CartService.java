package cart.service;

import cart.dao.cart.CartDao;
import cart.domain.Cart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public void add(Long memberId, Long productId) {
        cartDao.save(memberId, productId);
    }

    public List<Cart> findAll(Long memberId) {
        return cartDao.findAll(memberId);
    }

    public void delete(Long memberId, Long productId) {
        cartDao.delete(memberId, productId);
    }

}
