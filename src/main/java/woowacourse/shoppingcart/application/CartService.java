package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.User.User;
import woowacourse.shoppingcart.domain.cartitem.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.dto.CartItemRequest;
import woowacourse.shoppingcart.exception.AlreadyExistException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public List<CartItem> findCartsByCustomerName(final User user) {
        final List<Long> cartIds = findCartIdsByCustomerName(user.getUserName());
        final List<CartItem> carts = new ArrayList<>();

        for (final Long cartId : cartIds) {
            final long productId = cartItemDao.findProductIdByCartId(cartId);
            final Product product = productDao.findProductById(productId);
            final int quantity = cartItemDao.findQuantityById(cartId);
            carts.add(new CartItem(cartId, product, quantity));
        }
        return carts;
    }

    private List<Long> findCartIdsByCustomerName(final UserName userName) {
        final long customerId = customerDao.findIdByUserName(userName);
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public Long addCart(final Long productId, final User user) {
        final long customerId = customerDao.findIdByUserName(user.getUserName());
        validateAlreadyExist(productId, customerId);

        try {
            return cartItemDao.addCartItem(customerId, productId);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    private void validateAlreadyExist(Long productId, Long customerId) {
        if (cartItemDao.findIdByUserAndProduct(customerId, productId) != null) {
            throw new AlreadyExistException();
        }
    }

    public void deleteCart(final User user, final Long cartId) {
        validateCustomerCart(cartId, user.getUserName());
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(final Long cartId, final UserName userName) {
        if (checkNotContainsCartId(cartId, userName)) {
            throw new NotInCustomerCartItemException();
        }
    }

    private boolean checkNotContainsCartId(final Long cartId, final UserName userName) {
        final List<Long> cartIds = findCartIdsByCustomerName(userName);
        return !cartIds.contains(cartId);
    }

    public void updateQuantity(Long cartId, User user, CartItemRequest cartItemRequest) {
        final CartItem cartItem = getCartItemById(cartId);
        final long customerId = customerDao.findIdByUserName(user.getUserName());
        cartItem.updateQuantity(cartItemRequest.getQuantity());
        cartItemDao.updateQuantity(customerId, cartItem);
    }

    private CartItem getCartItemById(Long cartId) {
        Long productId = cartItemDao.findProductIdByCartId(cartId);
        Product product = productDao.findProductById(productId);
        int quantity = cartItemDao.findQuantityById(productId);
        return new CartItem(cartId, product, quantity);
    }
}
