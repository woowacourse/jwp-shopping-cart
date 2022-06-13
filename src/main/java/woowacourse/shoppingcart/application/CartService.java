package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dto.request.CartAddRequest;
import woowacourse.shoppingcart.dto.request.CartDeleteRequest;
import woowacourse.shoppingcart.dto.request.CartUpdateRequest;
import woowacourse.shoppingcart.dto.response.CartResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional
public class CartService {

    private final CartItemDao cartItemDao;

    private final CustomerDao customerDao;

    private final ProductDao productDao;

    public CartService(CartItemDao cartItemDao, CustomerDao customerDao, ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public List<CartResponse> findCartsByCustomerId(final Long customerId) {
        return cartItemDao.findCartsByCustomerId(customerId)
                .stream()
                .map(cart -> new CartResponse(cart.getId(), cart.getProductId(), cart.getQuantity(), cart.getName(),
                        cart.getPrice(),
                        cart.getImageUrl()))
                .collect(Collectors.toList());
    }

    public Long addCart(final Long customerId, final CartAddRequest cartAddRequest) {
        final Long productId = cartAddRequest.getProductId();
        customerDao.findById(customerId)
                .orElseThrow(InvalidCustomerException::new);
        productDao.findProductById(productId)
                .orElseThrow(InvalidProductException::new);

        return cartItemDao.addCartItem(customerId, productId);
    }

    public void updateCart(final Long customerId, final CartUpdateRequest cartUpdateRequest) {
        final Long productId = cartUpdateRequest.getProductId();
        validateProductInCustomerCart(productId, customerId);
        cartItemDao.updateCartItem(cartUpdateRequest.getQuantity(), customerId, productId);
    }

    public void deleteCart(final Long customerId, final CartDeleteRequest cartDeleteRequest) {
        final List<Long> cartIds = cartDeleteRequest.getCartIds();
        for (Long cartId : cartIds) {
            validateCustomerCart(cartId, customerId);
            cartItemDao.deleteCartItem(cartId);
        }
    }

    private void validateCustomerCart(final Long cartId, final Long customerId) {
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }

    private void validateProductInCustomerCart(final Long productId, final Long customerId) {
        final List<Long> productIdsInCarts = cartItemDao.findProductIdsByCustomerId(customerId);
        if (productIdsInCarts.contains(productId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
