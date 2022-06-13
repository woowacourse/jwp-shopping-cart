package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private static final int DELETED_ROW_BY_ID = 1;

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public Long addCart(final long productId, final long customerId) {
        productDao.findById(productId)
                .orElseThrow(InvalidProductException::new);
        return cartItemDao.save(customerId, productId);
    }

    @Transactional(readOnly = true)
    public CartResponse findCartByCustomerId(final long customerId) {
        final Cart cart = cartItemDao.findByCustomerId(customerId);
//        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);
//        final List<Product> products = findProducts(productIds);
        return CartResponse.of(cart);
    }

    private List<Product> findProducts(List<Long> productIds) {
        return productIds.stream()
                .map(id -> productDao.findById(id)
                        .orElseThrow(InvalidProductException::new))
                .collect(Collectors.toList());
    }

    public int deleteCart(final long customerId, final long productId) {
        validateCustomerCart(productId, customerId);
        final Long cartId = cartItemDao.findIdByCustomerIdAndProductId(customerId, productId);
        final int affectedRows = cartItemDao.deleteById(cartId);
        validateAffectedRows(affectedRows);
        return affectedRows;
    }

    private void validateCustomerCart(final long productId, final long customerId) {
        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);
        if (!productIds.contains(productId)) {
            throw new NotInCustomerCartItemException();
        }
    }

    private void validateAffectedRows(int affectedRows) {
        if (affectedRows != DELETED_ROW_BY_ID) {
            throw new InvalidCartItemException();
        }
    }
}
