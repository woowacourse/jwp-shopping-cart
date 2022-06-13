package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.exception.ExistCartItemException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service
public class CartService {

    private final CartDao cartDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(CartDao cartDao, CustomerDao customerDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.customerDao = customerDao;
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
        if (hasProductInCart(productId, customerUsername)) {
            throw new ExistCartItemException();
        }
        validateProductId(productId);
        cartDao.addCartItem(customerUsername, productId);
    }

    private boolean hasProductInCart(final Long productId, final String customerUsername) {
        return cartDao.findProductIdsByCustomerUsername(customerUsername)
                .contains(productId);
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
