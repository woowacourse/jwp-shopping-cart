package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.response.CartItemResponse;
import woowacourse.shoppingcart.dto.response.CartResponse;
import woowacourse.shoppingcart.exception.DuplicateCartItemByProduct;
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

    public CartResponse findCartByCustomerId(final Long customerId) {
        final Cart cart = cartItemDao.findCartByCustomerId(customerId);
        return CartResponse.of(cart);
    }

    public CartItemResponse addCart(final Long productId, final Long customerId) {
        if (cartItemDao.existByProduct(customerId, productId)) {
            throw new DuplicateCartItemByProduct();
        }

        final Product product = productDao.findProductById(productId);
        return CartItemResponse.of(cartItemDao.addCartItem(customerId, product));
    }

    public void deleteCartItem(final Long customerId, final Long cartItemId) {
        validateCustomerCart(cartItemId, customerId);
        cartItemDao.deleteCartItem(cartItemId);
    }

    private void validateCustomerCart(final Long cartItemId, final Long customerId) {
        Cart cart = cartItemDao.findCartByCustomerId(customerId);
        CartItem cartItem = cartItemDao.findByCartItemId(cartItemId);
        if (!cart.isContains(cartItem)) {
            throw new NotInCustomerCartItemException();
        }
    }

    public CartItemResponse updateCartItemQuantity(Long customerId, Long cartItemId, int quantity) {
        CartItem cartItem = cartItemDao.findByCartItemId(cartItemId);
        cartItemDao.updateCartItemQuantity(customerId, cartItemId, quantity);
        cartItem.updateQuantity(quantity);
        return CartItemResponse.of(cartItem);
    }

    public void deleteCart(Long customerId) {
        cartItemDao.deleteCart(customerId);
    }
}
