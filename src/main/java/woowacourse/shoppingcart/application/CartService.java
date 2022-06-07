package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.dto.request.CartAddRequest;
import woowacourse.shoppingcart.dto.request.CartDeleteRequest;
import woowacourse.shoppingcart.dto.request.CartUpdateRequest;
import woowacourse.shoppingcart.dto.response.CartResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;

    public CartService(CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    public List<CartResponse> findCartsByCustomerId(final Long customerId) {
        return cartItemDao.findCartsByCustomerId(customerId)
                .stream()
                .map(cart -> new CartResponse(cart.getId(), cart.getQuantity(), cart.getName(), cart.getPrice(),
                        cart.getImageUrl()))
                .collect(Collectors.toList());
    }

    public Long addCart(final Long customerId, final CartAddRequest cartAddRequest) {
        try {
            return cartItemDao.addCartItem(customerId, cartAddRequest.getProductId());
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void updateCart(final Long customerId, final CartUpdateRequest cartUpdateRequest) {
        cartItemDao.updateCartItem(cartUpdateRequest.getQuantity(), customerId, cartUpdateRequest.getProductId());
    }

    @Transactional
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
}
