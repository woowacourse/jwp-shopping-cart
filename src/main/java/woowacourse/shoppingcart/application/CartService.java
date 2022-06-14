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
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public List<CartResponse> findCartsByCustomerName(final String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);

        final List<CartResponse> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final int productQuantity = cartItemDao.findQuantityById(cartId);
            final Product product = productDao.findProductById(productId);
            carts.add(CartResponse.of(cartId, product, productQuantity));
        }
        return carts;
    }

    private List<Long> findCartIdsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);

        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public CartResponse addCart(final Long productId, final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        if (cartItemDao.existByProduct(customerId, productId)) {
            throw new DuplicateCartItemByProduct();
        }

        final Product product = productDao.findProductById(productId);
        return CartResponse.of(cartItemDao.addCartItem(customerId, product));
    }

    public void deleteCart(final String customerName, final Long cartId) {
        validateCustomerCart(cartId, customerName);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(final Long cartId, final String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);
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

    public void deleteAllCart(String customerName) {
        Long customerId = customerDao.findIdByUserName(customerName);
        cartItemDao.deleteCart(customerId);
    }
}
