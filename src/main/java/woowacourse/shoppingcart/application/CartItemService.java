package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.specification.CustomerSpecification;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;
    private final CustomerSpecification customerSpec;

    public List<CartItem> findCartsByCustomerId(final long customerId) {
        customerSpec.validateCustomerExists(customerId);
        final List<Long> cartIds = findCartIdsByCustomerId(customerId);

        final List<CartItem> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productDao.findProductById(productId);
            carts.add(new CartItem(cartId, product));
        }
        return carts;
    }

    public Long addCartItem(final Long productId, final long customerId, Long count) {
        customerSpec.validateCustomerExists(customerId);
        try {
            return cartItemDao.addCartItem(customerId, productId, count);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(final long customerId, final Long cartId) {
        customerSpec.validateCustomerExists(customerId);
        validateCustomerCart(cartId, customerId);
        cartItemDao.deleteCartItemById(cartId);
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
