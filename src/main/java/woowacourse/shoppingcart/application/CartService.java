package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.notfound.InvalidProductException;
import woowacourse.shoppingcart.exception.notfound.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private static final int INITIAL_QUANTITY = 1;

    private final CartItemDao cartItemDao;
    private final ProductService productService;

    public CartService(final CartItemDao cartItemDao, final ProductService productService) {
        this.cartItemDao = cartItemDao;
        this.productService = productService;
    }

    public Long addToCart(final Long customerId, final Long productId) {
        try {
            return cartItemDao.addCartItem(customerId, productId, INITIAL_QUANTITY);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public List<CartItem> findCartItemsByCustomerId(final Long customerId) {
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);

        final List<CartItem> cartItems = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productService.findProductById(productId);
            cartItems.add(new CartItem(cartId, product));
        }
        return cartItems;
    }

    public void deleteItems(final Long customerId, final List<Long> cartItemIds) {
        validateCartItems(customerId, cartItemIds);
        cartItemDao.deleteCartItems(cartItemIds);
    }

    private void validateCartItems(final Long customerId, final List<Long> cartIds) {
        final List<Long> findByCustomerId = cartItemDao.findIdsByCustomerId(customerId);
        if (containSameElements(cartIds, findByCustomerId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }

    private boolean containSameElements(final List<Long> from, final List<Long> to) {
        return from.containsAll(to) && to.containsAll(from);
    }

    public void update(final Long customerId, final Long productId, final Integer quantity) {
        final CartItem cartItem = cartItemDao.findByCustomerIdAndProductId(customerId, productId);
        final CartItem updatedCartItem = cartItem.applyQuantity(quantity);
        cartItemDao.update(updatedCartItem);
    }
}
