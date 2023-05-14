package cart.service;

import cart.dao.CartItemDao;
import cart.entity.CartItemEntity;
import cart.entity.ProductEntity;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartItemService {

    private final CartItemDao cartItemDao;

    public CartItemService(CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    @Transactional(readOnly = true)
    public List<ProductEntity> getCartItems(int memberId) {
        return cartItemDao.selectAllCartItems(memberId);
    }

    public void addCartItem(CartItemEntity cartItemEntity) {
        cartItemDao.addCartItem(cartItemEntity);
    }

    public void deleteCartItem(int cartItemId) {
        cartItemDao.deleteCartItem(cartItemId);
    }

}
