package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service
public class CartService {
    private final CartDao cartDao;
    private final ProductDao productDao;

    public CartService(CartDao cartDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public CartResponse findCartItemsByCustomerUsername(final String customerUsername) {
        final List<CartItemResponse> cartItemResponses = cartDao.findCartItemsByCustomerUsername(customerUsername)
                .stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toUnmodifiableList());
        return new CartResponse(cartItemResponses);
    }

    public void addCart(final Long productId, final String customerUsername) {
        validateProductId(productId);

        final List<CartItem> cartItems = cartDao.findCartItemsByCustomerUsername(customerUsername);
        final Cart cart = new Cart(cartItems);
        final Product product = productDao.findProductById(productId)
                .orElseThrow(InvalidProductException::new);
        cart.add(product);

        cartDao.addCartItem(customerUsername, productId);
    }

    public void updateCartItemQuantity(final int quantity, final Long productId, final String customerUsername) {
        validateProductId(productId);
        cartDao.updateCartItemQuantity(quantity, productId, customerUsername);
    }

    private void validateProductId(final Long productId) {
        productDao.findProductById(productId)
                .orElseThrow(() -> new InvalidProductException("존재하지 않는 상품입니다."));
    }

    public void deleteCart(final String customerUsername) {
        cartDao.deleteAllCartItems(customerUsername);
    }

    public void deleteCartItem(final String customerUsername, final List<Long> productIds) {
        validateCustomerCart(customerUsername, productIds);
        cartDao.deleteCartItem(productIds, customerUsername);
    }

    private void validateCustomerCart(final String customerUsername, final List<Long> productIdsToDelete) {
        final List<Long> productIds = cartDao.findProductIdsByCustomerUsername(customerUsername);
        if (!productIds.containsAll(productIdsToDelete)) {
            throw new NotInCustomerCartItemException();
        }
    }
}
