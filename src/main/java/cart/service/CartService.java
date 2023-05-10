package cart.service;

import cart.dao.h2Implement.CartH2Dao;
import cart.dto.CartProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {
    private final CartH2Dao cartH2Dao;

    public CartService(CartH2Dao cartH2Dao) {
        this.cartH2Dao = cartH2Dao;
    }

    @Transactional(readOnly = true)
    public List<CartProductResponse> getCartsByEmail(String email) {
        return cartH2Dao.selectById(email)
                .stream()
                .map(CartProductResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public int addCart(int productId, String email) {
        return cartH2Dao.insert(productId, email);
    }

    public void deleteCart(int cartId, String email) {
        int deleteResult = cartH2Dao.delete(cartId, email);
        if (deleteResult == 0) {
            throw new IllegalStateException("존재하지 않는 장바구니 입니다.");
        }
    }
}
