package woowacourse.shoppingcart.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CartItemIds;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotExistCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;

    public CartService(final CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    public CartItem findById(Customer customer, Long cartItemId) {
        return cartItemDao.findById(customer.getId(), cartItemId);
    }

    public List<CartItem> findCartItemsByCustomer(final Customer customer) {
        return cartItemDao.findCartItemsByCustomerId(customer.getId());
    }

    public Long addCart(final Customer customer, final Long productId, final int quantity) {
        try {
            return cartItemDao.addCartItem(customer, productId, quantity);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void updateQuantity(Customer customer, Long cartItemId, int quantity) {
        validateCustomerCartItem(customer, cartItemId);
        cartItemDao.updateQuantity(customer.getId(), cartItemId, quantity);
    }

    public void deleteCart(final Customer customer, final CartItemIds cartItemIds) {
        for (Long cartItemId : cartItemIds.getCartItemIds()) {
            validateCustomerCartItem(customer, cartItemId);
            cartItemDao.deleteCartItem(cartItemId);
        }
    }

    private void validateCustomerCartItem(Customer customer, Long cartItemId) {
        boolean result = cartItemDao.validateCustomerCartItem(customer.getId(), cartItemId);
        if (!result) {
            throw new NotExistCartItemException("장바구니에 없는 아이템입니다.", ErrorResponse.Not_EXIST_CART_ITEM);
        }
    }
}
