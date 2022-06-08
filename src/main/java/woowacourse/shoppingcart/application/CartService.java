package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.specification.CustomerSpecification;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.AlreadyCartItemExistException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NonExistProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.exception.OverQuantityException;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final CustomerSpecification customerSpec;

    public List<CartItem> findCartsByCustomerId(final long customerId) {
        final List<Long> cartIds = findCartIdsByCustomerId(customerId);

        final List<CartItem> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productDao.findProductById(productId);
            carts.add(new CartItem(cartId, product));
        }
        return carts;
    }

    public Long addCartItem(long customerId, long productId, long count) {
        customerSpec.validateCustomerExists(customerId);
        validateExistProduct(productId);
        validateExistCartItem(customerId, productId);
        validateIsOverQuantity(productId, count);
        try {
            return cartItemDao.addCartItem(customerId, productId, count);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    private void validateIsOverQuantity(long productId, long count) {
        Product findProduct = productDao.findProductById(productId);
        Long quantity = findProduct.getQuantity();
        if (quantity < count) {
            throw new OverQuantityException();
        }
    }

    private void validateExistProduct(long productId) {
        try {
            productDao.findProductById(productId);
        } catch (Exception e) {
            throw new NonExistProductException();
        }
    }

    private void validateExistCartItem(long customerId, long productId) throws AlreadyCartItemExistException {
        boolean exist = cartItemDao.existCartItem(customerId, productId);
        if (exist) {
            throw new AlreadyCartItemExistException();
        }
    }

    public void deleteCart(final long customerId, final Long cartId) {
        customerSpec.validateCustomerExists(customerId);
        validateCustomerCart(cartId, customerId);
        cartItemDao.deleteCartItemById(cartId);
    }

    public void changeCartItemCount(long customerId, int productId, int cartItemCount) {
        cartItemDao.updateCartItemDao(customerId, productId, cartItemCount);
    }

    private void validateCustomerCart(final Long cartId, final long customerId) {
        final List<Long> cartIds = findCartIdsByCustomerId(customerId);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }

    private List<Long> findCartIdsByCustomerId(final long customerId) {
        customerSpec.validateCustomerExists(customerId);
        return cartItemDao.findIdsByCustomerId(customerId);
    }
}
