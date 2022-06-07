package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.cartItem.CartItemResponse;
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
        final List<CartItem> cartItems = findCartIdsByEmail(email);

        return cartItems.stream()
                .map(CartItemResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    private List<CartItem> findCartIdsByEmail(final String email) {
        final Long customerId = customerDao.getIdByEmail(email);
        return cartItemDao.getAllByCustomerId(customerId);
    }

    public CartItemResponse findCart(Long cartId) {
        CartItem cartItem = cartItemDao.getById(cartId);
        return new CartItemResponse(cartItem);
    }

    public Long addCart(final Long productId, final int quantity, final String email) {
        final Long customerId = customerDao.getIdByEmail(email);
        try {
            return cartItemDao.save(customerId, quantity, productId);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCarts(final String email, final List<Long> cartIds) {
        for (Long cartId : cartIds) {
            validateCustomerCart(cartId, email);
            cartItemDao.delete(cartId);
        }
    }

    private void validateCustomerCart(final Long cartId, final String email) {
        List<Long> cartIds = findCartIdsByEmail(email).stream()
                .map(CartItem::getId)
                .collect(Collectors.toUnmodifiableList());
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }

    public void updateCart(final String email, final Long cartItemId, final int quantity) {
        validateCustomerCart(cartItemId, email);
        CartItem oldCartItem = cartItemDao.getById(cartItemId);
        CartItem newCartItem = new CartItem(oldCartItem.getId(), quantity, oldCartItem.getProduct());
        cartItemDao.updateCartQuantity(newCartItem);
    }
}
