package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.AccountDao;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Account;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.DuplicateCartProductException;
import woowacourse.shoppingcart.exception.InvalidAccountException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInAccountCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final AccountDao accountDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final AccountDao accountDao,
                       final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.accountDao = accountDao;
        this.productDao = productDao;
    }

    public List<Cart> findCartsByCustomerName(final String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);

        final List<Cart> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productDao.findProductById(productId)
                .orElseThrow(InvalidProductException::new);
            carts.add(new Cart(cartId, product));
        }
        return carts;
    }

    private List<Long> findCartIdsByCustomerName(final String customerName) {
        final Long customerId = accountDao.findIdByUserName(customerName);
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public Long addCart(final Long productId, final String customerName) {
        final Long customerId = accountDao.findIdByUserName(customerName);
        try {
            return cartItemDao.addCartItem(customerId, productId);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(final String customerName, final Long cartId) {
        validateCustomerCart(cartId, customerName);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(final Long cartId, final String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInAccountCartItemException();
    }

    public Long addProduct(String email, long productId) {
        Account account = accountDao.findByEmail(email)
            .orElseThrow(InvalidAccountException::new);
        Product product = productDao.findProductById(productId)
            .orElseThrow(InvalidProductException::new);

        if (cartItemDao.existByAccountIdAndProductId(account.getId(), product.getId())) {
            throw new DuplicateCartProductException();
        }

        return cartItemDao.save(new CartItem(product, 1, account.getId()));
    }

    public List<CartItem> findCartsByEmail(String email) {
        Account account = accountDao.findByEmail(email)
            .orElseThrow(InvalidAccountException::new);
        return cartItemDao.findByAccountId(account.getId());
    }
}
