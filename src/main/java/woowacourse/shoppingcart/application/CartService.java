package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public boolean existsCartItems(final Long customerId, final Long productId) {
        return cartItemDao.hasCartItem(productId, customerId);
    }

    public List<CartResponse> findCartsByCustomerId(final Long customerId) {
        final List<Long> cartIds = findCartIdsByCustomerId(customerId);

        final List<Cart> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productDao.findProductById(productId);
            final int quantity = cartItemDao.findQuantityById(cartId);
            carts.add(new Cart(cartId, product, quantity));
        }
        return carts.stream()
                .map(CartResponse::from)
                .collect(Collectors.toList());
    }

    public List<Long> findCartIdsByCustomerId(final Long customerId) {
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public Long addCart(final CartRequest request, Long customerId) {
        try {
            return cartItemDao.addCartItem(customerId, request.getProductId(), request.getQuantity());
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(final Long cartId, final Long customerId) {
        validateCustomerCart(cartId, customerId);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(final Long cartId, final Long customerId) {
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
