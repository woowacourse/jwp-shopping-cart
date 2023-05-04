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
        // TODO: 2023-05-04 Cart도메인으로 완성시키고 dao에서 save하는 게 낫나? 불필요하게 Product객체 생성하는 거 아닌가.id를 넘기면 더 효율적인데?
        cartDao.save(cartRequest.getMemberId(), cartRequest.getProductId());
    }

    public void addProduct(long memberId, long productId) {
        // TODO: 2023-05-04 Cart도메인으로 완성시키고 dao에서 save하는 게 낫나? 불필요하게 Product객체 생성하는 거 아닌가.id를 넘기면 더 효율적인데?
        cartDao.insertProduct(memberId, productId);
    }

    public CartDto findById(long memberId) {
        Cart cart = cartDao.findByMemberId(memberId);
        return new CartDto(cart);
    }

    public void removeProduct(long memberId, long productId) {
        cartDao.deleteProduct(memberId, productId);
    }
}
