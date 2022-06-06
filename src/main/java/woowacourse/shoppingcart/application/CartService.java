package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
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

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public CartResponse findCartByCustomerId(final long customerId) {
        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);

        final List<Product> products = productIds.stream()
                .map(id -> productDao.findById(id)
                        .orElseThrow(InvalidProductException::new))
                .collect(Collectors.toList());

        return CartResponse.of(products);
    }

    public Long addCart(final long productId, final long customerId) {
        productDao.findById(productId).orElseThrow(InvalidProductException::new);
        return cartItemDao.addCartItem(customerId, productId);
    }

    public int deleteCart(final long customerId, final long productId) {
        validateCustomerCart(productId, customerId);
        final Long cartId = cartItemDao.findIdByCustomerIdAndProductId(customerId, productId);
        final int affectedRows = cartItemDao.deleteById(cartId);
        if (affectedRows != 1) {
            throw new InvalidCartItemException();
        }
        return affectedRows;
    }

    private void validateCustomerCart(final long productId, final long customerId) {
        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);
        if (productIds.contains(productId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
