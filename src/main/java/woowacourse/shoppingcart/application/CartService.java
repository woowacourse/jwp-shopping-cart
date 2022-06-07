package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.cart.Quantity;
import woowacourse.shoppingcart.domain.customer.CustomerId;
import woowacourse.shoppingcart.domain.product.ProductId;
import woowacourse.shoppingcart.dto.CartItemRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.exception.InvalidTokenException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final JwtTokenProvider jwtTokenProvider;

    public CartService(final CartItemDao cartItemDao, JwtTokenProvider jwtTokenProvider) {
        this.cartItemDao = cartItemDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public CartItemResponse addCart(String token, CartItemRequest cartItemRequest) {
        validateToken(token);
        final CustomerId customerId = new CustomerId(Long.parseLong(jwtTokenProvider.getPayload(token)));
        cartItemDao.save(customerId, new ProductId(cartItemRequest.getId()), new Quantity(cartItemRequest.getQuantity()));
        return new CartItemResponse(customerId.getValue(), cartItemRequest.getQuantity());
    }

    private void validateToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException();
        }
    }
}
