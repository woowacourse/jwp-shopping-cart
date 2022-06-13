package woowacourse.shoppingcart.repository;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import woowacourse.auth.dto.LoginCustomer;
import woowacourse.exception.shoppingcart.InvalidProductException;
import woowacourse.exception.shoppingcart.NotInCustomerCartItemException;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.dto.CartItem;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Customer;

@Repository
public class CartItemRepository {
    private final CustomerDao customerDao;
    private final CartItemDao cartItemDao;

    public CartItemRepository(CustomerDao customerDao, CartItemDao cartItemDao) {
        this.customerDao = customerDao;
        this.cartItemDao = cartItemDao;
    }

    public List<Cart> findCartsByLoginId(String loginId) {
        List<CartItem> cartItems = cartItemDao.findCartsByLoginId(loginId);
        return cartItems.stream()
                .map(Cart::new)
                .collect(Collectors.toList());
    }

    public Cart add(LoginCustomer loginCustomer, long productId, int quantity) {
        Customer customer = customerDao.findByLoginId(loginCustomer.getLoginId());
        Long customerId = customer.getId();
        try {
            Long cartId = cartItemDao.addCartItem(customerId, productId, quantity);
            CartItem cartItem = cartItemDao.findCartByCartId(cartId);
            return new Cart(cartId, cartItem.getProduct(), cartItem.getQuantity());
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void delete(String loginId, Long cartId) {
        validateCustomerCart(cartId, loginId);
        cartItemDao.deleteCartItem(cartId);
    }

    public void deleteAll(String loginId) {
        Customer customer = customerDao.findByLoginId(loginId);
        cartItemDao.deleteAllCart(customer.getId());
    }

    public Cart update(String loginId, int quantity, Long cartId) {
        validateCustomerCart(cartId, loginId);
        cartItemDao.updateQuantity(cartId, quantity);
        return new Cart(cartItemDao.findCartByCartId(cartId));
    }

    private void validateCustomerCart(final Long cartId, final String loginId) {
        final List<Long> cartIds = cartItemDao.findIdsByLoginId(loginId);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
