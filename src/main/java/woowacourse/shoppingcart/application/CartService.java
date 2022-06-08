package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.AccountDao;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Account;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.DuplicateCartProductException;
import woowacourse.shoppingcart.exception.InvalidAccountException;
import woowacourse.shoppingcart.exception.InvalidProductException;

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

    public void deleteProduct(String email, long productId) {
        Account account = accountDao.findByEmail(email)
            .orElseThrow(InvalidAccountException::new);
        cartItemDao.deleteCartItem(account.getId(), productId);
    }

    public void updateProduct(String email, long productId, int quantity) {
        Account account = accountDao.findByEmail(email)
            .orElseThrow(InvalidAccountException::new);
        Product product = productDao.findProductById(productId)
            .orElseThrow(InvalidProductException::new);
        cartItemDao.updateCartItem(account.getId(), product.getId(), quantity);
    }
}
