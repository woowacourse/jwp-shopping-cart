package woowacourse.shoppingcart.repository;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import woowacourse.auth.domain.user.Customer;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.entity.CartItemEntity;

@Repository
public class CartItemRepository {

    private final CartItemDao cartItemDao;

    public CartItemRepository(CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    // TODO: sort ASC by cartItemId
    public Cart findAllByCustomer(Customer customer) {
        List<CartItem> cartItems = cartItemDao.findAllByCustomerId(customer.getId())
                .stream()
                .map(CartItemEntity::toDomain)
                .collect(Collectors.toList());
        return new Cart(cartItems);
    }

    public void save(Customer customer, CartItem cartItem) {
        cartItemDao.save(new CartItemEntity(customer, cartItem));
    }

    public void updateQuantity(Customer customer, CartItem cartItem) {
        cartItemDao.updateByCustomerIdAndProductId(new CartItemEntity(customer, cartItem));
    }

    public void deleteByCustomer(Customer customer) {
        cartItemDao.deleteAllByCustomerId(customer.getId());
    }

    public void deleteByCustomerAndProductIds(Customer customer, List<Long> productIds) {
        cartItemDao.deleteByCustomerIdAndProductIds(customer.getId(), productIds);
    }
}
