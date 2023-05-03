package cart.service;

import cart.dao.CartItemDao;
import cart.entity.CartItemEntity;
import cart.entity.ProductEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartItemService {

    private final CartItemDao cartItemDao;

    public CartItemService(CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    public List<ProductEntity> getCartItems(int memberId) {
        return cartItemDao.selectAllCartItems(memberId);
    }

    public void addCartItem(CartItemEntity cartItemEntity) {

    }

    public void deleteCartItem(int cartItemId) {
        cartItemDao.removeCartItem(cartItemId);
    }

}
