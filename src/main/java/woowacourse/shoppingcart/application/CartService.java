package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;

    public CartService(CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    public void addCart(long memberId, long productId, int quantity) {
        if (cartItemDao.isExistsMemberIdAndProductId(memberId, productId)) {
            cartItemDao.increaseQuantity(memberId, productId, quantity);
            return;
        }
        cartItemDao.addCartItem(memberId, productId, quantity);
    }
}
