package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
    }

    public List<CartItemResponse> findCartsByEmail(final String email) {
        final List<Cart> carts = findCartIdsByEmail(email);

        return carts.stream()
                .map(CartItemResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    private List<Cart> findCartIdsByEmail(final String email) {
        final Long customerId = customerDao.getIdByEmail(email);
        return cartItemDao.getAllByCustomerId(customerId);
    }

    public CartItemResponse findCart(Long cartId) {
        Cart cart = cartItemDao.getById(cartId);
        return new CartItemResponse(cart);
    }

    public Long addCart(final Long productId, final int quantity, final String email) {
        final Long customerId = customerDao.getIdByEmail(email);
        try {
            return cartItemDao.addCartItem(customerId, quantity, productId);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(final String email, final Long cartId) {
        validateCustomerCart(cartId, email);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(final Long cartId, final String email) {
        List<Long> cartIds = findCartIdsByEmail(email).stream()
                .map(Cart::getId)
                .collect(Collectors.toUnmodifiableList());
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }

    public void updateCart(final String email, final Long cartItemId, final int quantity) {
        validateCustomerCart(cartItemId, email);
        Cart oldCart = cartItemDao.getById(cartItemId);
        Cart newCart = new Cart(oldCart.getId(), quantity, oldCart.getProduct());
        cartItemDao.updateCartQuantity(newCart);
    }
}
