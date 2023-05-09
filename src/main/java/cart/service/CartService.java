package cart.service;

import cart.dao.h2Implement.CartH2Dao;
import cart.dao.h2Implement.CartProductH2Dao;
import cart.dto.CartProductResponse;
import cart.entity.Cart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartService {
    private final CartH2Dao cartH2Dao;

    private final CartProductH2Dao cartProductH2Dao;

    public CartService(CartH2Dao cartH2Dao, CartProductH2Dao cartProductH2Dao) {
        this.cartH2Dao = cartH2Dao;
        this.cartProductH2Dao = cartProductH2Dao;
    }

    @Transactional(readOnly = true)
    public List<CartProductResponse> getCartsByEmail(String email) {
        return cartProductH2Dao.selectById(email);
    }

    public int addCart(int productId, String email) {
        Cart cart = new Cart(productId, email);
        return cartH2Dao.insert(cart);
    }

    public void deleteCart(int cartId, String email) {
        int deleteResult = cartH2Dao.delete(cartId, email);
        if (deleteResult == 0) {
            throw new IllegalStateException("존재하지 않는 장바구니 입니다.");
        }
    }
}
