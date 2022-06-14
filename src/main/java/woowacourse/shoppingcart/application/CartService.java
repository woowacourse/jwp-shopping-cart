package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.cart.CartItem;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.exception.CartItemNotFoundException;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
    }

    public List<CartItemResponse> findCartsByCustomerName(final String customerName) {
        Long customerId = customerDao.findIdByName(customerName);
        List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(customerId);
        return cartItems.stream()
                .map(cartItem -> new CartItemResponse(cartItem.getId(),
                        cartItem.getQuantity(),
                        ProductResponse.from(cartItem.getProduct())))
                .collect(Collectors.toList());
    }

    private List<Long> findCartIdsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByName(customerName);
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public Long addCart(final Long productId, final int quantity, final String customerName) {
        final Long customerId = customerDao.findIdByName(customerName);
        try {
            return cartItemDao.addCartItem(customerId, productId, quantity);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(final String customerName, final Long cartId) {
        validateCustomerCart(cartId, customerName);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(final Long cartId, final String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new CartItemNotFoundException();
    }

    public void updateCartItem(String customerName, final int updateQuantity, final Long cartItemId) {
        final Long customerId = customerDao.findIdByName(customerName);
        final CartItem cartItem = cartItemDao.findCartItemsByCustomerId(customerId).stream()
                .filter(it -> it.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(CartItemNotFoundException::new);
        final CartItem updatedCartItem = cartItem.update(updateQuantity);
        cartItemDao.updateCartItem(updatedCartItem);
    }
}
