package woowacourse.shoppingcart.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.specification.CustomerSpecification;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.AlreadyCartItemExistException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotExistProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.exception.OverQuantityException;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final CustomerSpecification customerSpec;

    public Long addCartItem(long customerId, long productId, long cartItemCount) {
        customerSpec.validateCustomerExists(customerId);
        validateExistProduct(productId);
        validateExistCartItem(customerId, productId);
        Product findProduct = validateExistProductAndGet(productId);
        validateOverQuantity(findProduct, cartItemCount);

        try {
            return cartItemDao.addCartItem(customerId, productId, cartItemCount);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void changeCartItemCount(long customerId, int productId, long cartItemCount) {
        Product findProduct = validateExistProductAndGet(productId);
        validateOverQuantity(findProduct, cartItemCount);

        cartItemDao.updateCartItemDao(customerId, productId, cartItemCount);
    }

    public void deleteCart(final long customerId, final Long productId) {
        customerSpec.validateCustomerExists(customerId);
        validateCustomersCart(customerId, productId);

        cartItemDao.deleteCartItemByProductId(productId);
    }

    private void validateExistCartItem(long customerId, long productId) throws AlreadyCartItemExistException {
        boolean exist = cartItemDao.existCartItem(customerId, productId);
        if (exist) {
            throw new AlreadyCartItemExistException();
        }
    }

    private void validateOverQuantity(Product findProduct, long cartItemCount) {
        Long quantity = findProduct.getQuantity();
        if (quantity < cartItemCount) {
            throw new OverQuantityException();
        }
    }

    private Product validateExistProductAndGet(long productId) {
        try {
            return productDao.findProductById(productId);
        } catch (Exception e) {
            throw new NotExistProductException();
        }
    }

    private void validateExistProduct(long productId) {
        try {
            productDao.findProductById(productId);
        } catch (Exception e) {
            throw new NotExistProductException();
        }
    }

    private void validateCustomersCart(final long customerId, final Long productId) {
        final List<Long> cartIds = findCartIdsByCustomerId(customerId);
        if (cartIds.contains(productId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }

    private List<Long> findCartIdsByCustomerId(final long customerId) {
        customerSpec.validateCustomerExists(customerId);
        return cartItemDao.findProductIdsByCustomerId(customerId);
    }
}
