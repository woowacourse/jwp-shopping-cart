package cart.service;

import cart.dao.CartDao;
import cart.domain.Cart;
import cart.dto.CartDto;
import cart.dto.CartRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CartService {

    private CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public void addProducts(CartRequest cartRequest) {
        cartDao.save(cartRequest.getMemberId(), cartRequest.getProductId());
    }

    public void addProduct(long memberId, long productId) {
        cartDao.insertProduct(memberId, productId);
    }

    @Transactional(readOnly = true)
    public CartDto findById(long memberId) {
        Cart cart = cartDao.findByMemberId(memberId);
        return new CartDto(cart);
    }

    public void removeProduct(long memberId, long productId) {
        cartDao.deleteProduct(memberId, productId);
    }
}
