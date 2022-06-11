package woowacourse.shoppingcart.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.LoginCustomer;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.cart.CartAddRequest;
import woowacourse.shoppingcart.dto.cart.CartUpdateRequest;
import woowacourse.shoppingcart.repository.CartItemRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private static final int DEFAULT_QUANTITY = 1;

    private final CartItemRepository cartItemRepository;

    public CartService(final CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional(readOnly = true)
    public List<Cart> findCartsByLoginCustomer(LoginCustomer loginCustomer) {
        return cartItemRepository.findCartsByLoginId(loginCustomer.getLoginId());
    }

    public Cart addCart(final LoginCustomer loginCustomer, final CartAddRequest request) {
        return cartItemRepository.add(loginCustomer, request.getProductId(), DEFAULT_QUANTITY);
    }

    public void deleteCart(final LoginCustomer loginCustomer, final Long cartId) {
        cartItemRepository.delete(loginCustomer.getLoginId(), cartId);
    }

    public void deleteAllCart(LoginCustomer loginCustomer) {
        cartItemRepository.deleteAll(loginCustomer.getLoginId());
    }

    public Cart updateQuantity(LoginCustomer loginCustomer, CartUpdateRequest request, Long cartId) {
        return cartItemRepository.update(loginCustomer.getLoginId(), request.getQuantity(), cartId);
    }
}
