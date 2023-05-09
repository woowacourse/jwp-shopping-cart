package cart.service;

import cart.dao.CartDao;
import cart.dto.CartProductResponse;
import cart.entity.Cart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartService {
    private final CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Transactional(readOnly = true)
    public List<CartProductResponse> getCartsByEmail(String email) {
        return cartDao.selectById(email);
    }

    public int addCart(int productId, String email) {
        Cart cart = new Cart(productId, email);
        return cartDao.insert(cart);
    }

    public void deleteCart(int cartId, String email) {
        int deleteResult = cartDao.delete(cartId, email);
        if (deleteResult == 0) {
            throw new IllegalStateException("존재하지 않는 장바구니 입니다.");
        }
    }
}
