package cart.service;

import cart.dao.CartDao;
import cart.dto.CartProductDto;
import cart.entity.Cart;
import cart.entity.CartProduct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public List<CartProductDto> getCartsByEmail(String email) {
        List<CartProduct> cartProducts = cartDao.selectById(email);
        return cartProducts.stream()
                .map(CartProductDto::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public int addCart(int productId, String email) {
        Cart cart = new Cart(productId, email);
        return cartDao.insert(cart);
    }

    public void deleteCart(int cartId, String email) {
        int deleteResult = cartDao.delete(cartId, email);
        if(deleteResult == 0){
            throw new IllegalStateException("존재하지 않는 장바구니 입니다.");
        }
    }
}
