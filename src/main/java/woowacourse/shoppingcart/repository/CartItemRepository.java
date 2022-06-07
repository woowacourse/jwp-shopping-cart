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

    public List<CartItem> findCartItemsByCustomerId(final Long customerId) {
        return cartItemDao.findCartItemsByCustomerId(customerId);
    }

    public List<CartItem> addCartItems(final Long customerId, final List<Long> productIds) {
        for (Long productId : productIds) {
            addCartItem(customerId, productId);
        }
        return productIds.stream()
                .map(it -> cartItemDao.findByCustomerIdAndProductId(customerId, it))
                .collect(Collectors.toList());
    }

    private void addCartItem(final Long customerId, final Long productId) {
        CartItem cartItem = cartItemDao.findByCustomerIdAndProductId(customerId, productId);
        if (cartItem != null) {
            cartItemDao.updateCartItem(cartItem.getId(), cartItem.getQuantity() + 1);
        }
        if (cartItem == null) {
            cartItemDao.create(customerId, productId);
        }
    }

    public CartItem updateCartItem(final CartItem cartItem) {
        cartItemDao.updateCartItem(cartItem.getId(), cartItem.getQuantity());
        return cartItem;
    }

    public void delete(final Long cartItemId) {
        cartItemDao.delete(cartItemId);
    }
}
