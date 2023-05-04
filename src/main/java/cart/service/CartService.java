package cart.service;

import cart.controller.dto.CartResponse;
import cart.dao.CartDao;
import cart.entity.CartEntity;
import java.util.List;
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

    public List<CartResponse> findProductsByMemberId(Long id) {
        return cartDao.findProductsByMemberId(id);
    }

    public void deleteById(final Long cartId) {
        cartDao.deleteById(cartId);
    }
}
