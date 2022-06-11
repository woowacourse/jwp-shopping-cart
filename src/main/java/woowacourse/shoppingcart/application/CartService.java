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

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartDao cartDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(CartDao cartDao, CustomerDao customerDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public CartResponse findCartsByCustomerName(final String customerUsername) {
        final Long customerId = customerDao.findIdByUsername(customerUsername);
        final List<CartItemResponse> cartItemResponses = cartDao.findCartsByCustomerId(customerId)
                .stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toUnmodifiableList());
        return new CartResponse(cartItemResponses);
    }

    public void addCart(final Long productId, final String customerUsername) {
        if (hasProductInCart(productId, customerUsername)) {
            throw new ExistCartItemException();
        }
        final Long customerId = customerDao.findIdByUsername(customerUsername);
        validateProductId(productId);
        cartDao.addCartItem(customerId, productId);
    }

    private boolean hasProductInCart(final Long productId, final String customerUsername) {
        final Long customerId = customerDao.findIdByUsername(customerUsername);
        return cartDao.findProductIdsByCustomerId(customerId).contains(productId);
    }

    public void updateCartItemQuantity(final int quantity, final Long productId, final String customerUsername) {
        final Long customerId = customerDao.findIdByUsername(customerUsername);
        validateProductId(productId);
        cartDao.updateCartItemQuantity(quantity, productId, customerId);
    }

    private void validateProductId(final Long productId) {
        productDao.findProductById(productId)
                .orElseThrow(() -> new InvalidProductException("존재하지 않는 상품입니다."));
    }

    public void deleteCart(final String customerUsername) {
        final Long customerId = customerDao.findIdByUsername(customerUsername);
        cartDao.deleteCartByCustomerId(customerId);
    }

    public void deleteCartItem(final String customerUsername, final List<Long> productIds) {
        final Long customerId = customerDao.findIdByUsername(customerUsername);
        validateCustomerCart(customerId, productIds);
        for (Long productId : productIds) {
            cartDao.deleteCartItem(productId, customerId);
        }
    }

    private void validateCustomerCart(final Long customerId, final List<Long> productIdsToDelete) {
        final List<Long> productIds = cartDao.findProductIdsByCustomerId(customerId);
        if (productIds.containsAll(productIdsToDelete)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
