package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    public static final int MINIMUM_CART_QUANTITY = 0;

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public List<Cart> findCartsByCustomerId(Long id) {
        final List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(id);
        final List<Cart> carts = new ArrayList<>();
        for (final CartItem cartItem : cartItems) {
            final Long productId = cartItemDao.findProductIdById(cartItem.getId());
            final Product product = productDao.findProductById(productId);
            carts.add(new Cart(cartItem.getId(), product, cartItem.getQuantity()));
        }
        return carts;
    }

    public Long addCart(final Long customerId, final Long productId, final int quantity) {
        validateMinimumQuantity(quantity);
        try {
            return cartItemDao.addCartItem(customerId, productId, quantity);
        } catch (Exception e) {
            throw new InvalidProductException("해당하는 상품이 없습니다.");
        }
    }

    public void deleteCart(final Long customerId, final List<Long> productIds) {
        for (Long productId : productIds) {
            validateCustomerCart(productId, customerId);
            cartItemDao.deleteCartItem(customerId, productId);
        }
    }

    public void updateCart(Long customerId, Long productId, int quantity) {
        validateMinimumQuantity(quantity);
        validateCustomerCart(productId, customerId);
        cartItemDao.modifyQuantity(customerId, productId, quantity);
    }

    private void validateMinimumQuantity(int quantity) {
        if (quantity <= MINIMUM_CART_QUANTITY) {
            throw new IllegalArgumentException("올바르지 않은 상품 수량 형식입니다.");
        }
    }

    private void validateCustomerCart(final Long productId, final Long customerId) {
        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);
        if (productIds.contains(productId)) {
            return;
        }
        throw new NotInCustomerCartItemException("해당하는 상품이 없습니다.");
    }
}
