package woowacourse.shoppingcart.repository;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.repository.dao.CartItemDao;

@Repository
public class CartItemRepository {

    private final CartItemDao cartItemDao;

    public CartItemRepository(final CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    public List<CartItem> selectCartItemsByCustomerId(final Long customerId) {
        return cartItemDao.selectCartItemsByCustomerId(customerId);
    }

    public List<CartItem> addCartItems(final Long customerId, final List<Long> productIds) {
        for (Long productId : productIds) {
            addCartItem(customerId, productId);
        }
        return productIds.stream()
                .map(it -> cartItemDao.selectByCustomerIdAndProductId(customerId, it))
                .collect(Collectors.toList());
    }

    private void addCartItem(final Long customerId, final Long productId) {
        boolean isExist = cartItemDao.existsCustomerIdAndProductId(customerId, productId);
        if (isExist) {
            CartItem cartItem = cartItemDao.selectByCustomerIdAndProductId(customerId, productId);
            cartItemDao.updateQuantity(cartItem.getId(), cartItem.getQuantity() + 1);
        }
        if (!isExist) {
            cartItemDao.insert(customerId, productId);
        }
    }

    public CartItem updateQuantity(final CartItem cartItem) {
        cartItemDao.updateQuantity(cartItem.getId(), cartItem.getQuantity());
        return cartItem;
    }

    public void delete(final Long cartItemId) {
        cartItemDao.delete(cartItemId);
    }
}
