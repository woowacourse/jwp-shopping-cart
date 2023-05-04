package cart.service;

import cart.dao.CartDao;
import cart.entity.CartEntity;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public Long addProduct(Long memberId, Long productId) {
        return cartDao.save(new CartEntity(memberId, productId));
    }

}
