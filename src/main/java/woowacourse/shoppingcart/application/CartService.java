package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Cart> findCarts(final String userName) {
        final List<Long> cartIds = findCartIdsByCustomerName(userName);

        return cartIds.stream()
                .map(this::assembleCartWithId)
                .collect(Collectors.toList());
    }

    private List<Long> findCartIdsByCustomerName(final String userName) {
        final Long customerId = customerDao.findIdByUserName(userName);
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    private Cart assembleCartWithId(Long cartId) {
        final Long productId = cartItemDao.findProductIdById(cartId);
        final int quantity = cartItemDao.findQuantityByCartId(cartId);
        final Product product = productDao.findProductById(productId);
        return new Cart(cartId, product, quantity);
    }

    public Long addCart(final String userName, final Long productId) {
        final Long customerId = customerDao.findIdByUserName(userName);

        if(cartItemDao.existByCustomerIdAndProductId(customerId, productId)){
            return cartItemDao.findIdByCustomerIdAndProductId(customerId, productId);
        }

        try {
            return cartItemDao.addCartItem(customerId, productId);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(final String userName, final Long cartId) {
        validateCustomerCart(cartId, userName);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(final Long cartId, final String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);
        if (!cartIds.contains(cartId)) {
            throw new NotInCustomerCartItemException();
        }
    }

    public void updateQuantity(Long cartId, int quantity) {
        cartItemDao.updateProductQuantity(cartId, quantity);
    }
}
