package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.response.CartResponse;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.repository.CartRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartRepository cartRepository;

    public CartService(final CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<CartResponse> findCartsByCustomerName(final String customerName) {
        final List<Cart> carts = cartRepository.findCartsByCustomerName(customerName);
        return carts.stream()
                .map(CartResponse::from)
                .collect(Collectors.toList());
    }

    public Long addCart(final Long productId, final String customerName) {
        return cartRepository.addCart(productId, customerName);
    }

    public void deleteCart(final String customerName, final Long cartId) {
        validateCustomerCart(cartId, customerName);
        cartRepository.deleteCart(cartId);
    }

    private void validateCustomerCart(final Long cartId, final String customerName) {
        final List<Long> cartIds = cartRepository.findCartIdsByCustomerName(customerName);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
