package cart.service;

import cart.dao.CartDao;
import cart.domain.CartEntity;
import cart.dto.AuthInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public void addItem(String memberId, int productId) {
        cartDao.insert(memberId, productId);
    }

    public List<CartEntity> searchItems(AuthInfo authInfo) {
        return cartDao.findAll(authInfo.getEmail());
    }

    public void deleteItem(int cartId) {
        cartDao.deleteById(cartId);
    }
}
