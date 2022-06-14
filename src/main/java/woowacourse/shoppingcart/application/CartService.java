package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.response.CartResponse;
import woowacourse.shoppingcart.exception.DuplicateCartItemByProduct;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public List<CartResponse> findCartsByCustomerId(final Long customerId) {
        final List<Long> cartIds = findCartIdsByCustomerId(customerId);

        final List<CartResponse> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final int productQuantity = cartItemDao.findQuantityById(cartId);
            final Product product = productDao.findProductById(productId);
            carts.add(CartResponse.of(cartId, product, productQuantity));
        }
        return carts;
    }

    private List<Long> findCartIdsByCustomerId(final Long customerId) {
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public CartResponse addCart(final Long productId, final Long customerId) {
        if (cartItemDao.existByProduct(customerId, productId)) {
            throw new DuplicateCartItemByProduct();
        }

        final Product product = productDao.findProductById(productId);
        return CartResponse.of(cartItemDao.addCartItem(customerId, product));
    }

    public void deleteCartItem(final Long customerId, final Long cartId) {
        validateCustomerCart(cartId, customerId);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(final Long cartId, final Long customerId) {
        final List<Long> cartIds = findCartIdsByCustomerId(customerId);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }

    public CartResponse updateCardItemQuantity(Long customerId, Long cartId, int quantity) {
        Cart cart = cartItemDao.findByCartId(cartId);
        cartItemDao.updateCartItemQuantity(customerId, cartId, quantity);
        cart.updateQuantity(quantity);
        return CartResponse.of(cart);
    }

    public void deleteCart(Long customerId) {
        cartItemDao.deleteCart(customerId);
    }
}
