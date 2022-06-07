package woowacourse.shoppingcart.application;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public CartItemResponse addCartItem(Long productId, Integer quantity, String email) {
        final Customer customer = customerDao.findByEmail(email);

        final Long id = cartItemDao.addCartItem(customer.getId(), productId, quantity);
        final Product product = productDao.findProductById(productId);
        return CartItemResponse.of(id, quantity, product);
    }

    public List<CartItemResponse> findCartItems(String email) {
        final Customer customer = customerDao.findByEmail(email);
        final List<CartItem> cartItems = cartItemDao.findAllByCustomerId(customer.getId());
        return cartItems.stream()
                .map(cartItem -> CartItemResponse.of(cartItem.getId(), cartItem.getQuantity(), cartItem.getProduct()))
                .collect(Collectors.toUnmodifiableList());
    }

    public CartItemResponse findCartItem(String email, Long cartItemId) {
        final Customer customer = customerDao.findByEmail(email);
        final CartItem cartItem = cartItemDao.findByCustomerId(customer.getId(), cartItemId);
        return CartItemResponse.of(cartItem.getId(), cartItem.getQuantity(), cartItem.getProduct());
    }

    public void updateQuantity(String email, Long cartItemId, Integer quantity) {
        final Customer customer = customerDao.findByEmail(email);
        cartItemDao.updateQuantityByCustomerId(customer.getId(), cartItemId, quantity);
    }

    public void deleteCartItem(String email, List<Long> cartItemIds) {
        final Customer customer = customerDao.findByEmail(email);
        validateCustomerCart(cartItemIds, customer.getId());

        cartItemDao.deleteCartItemByIds(cartItemIds);
    }

    private void validateCustomerCart(List<Long> cartItemIds, Long customerId) {
        final List<CartItem> cartItems = cartItemDao.findAllByCustomerId(customerId);
        final Set<Long> savedCartItemIds = new HashSet<>(cartItems.stream()
                .map(CartItem::getId)
                .collect(Collectors.toUnmodifiableList()));
        if (containsAllIds(cartItemIds, savedCartItemIds)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }

    private boolean containsAllIds(List<Long> cartItemIds, Set<Long> savedCartItemIds) {
        return cartItemIds.stream()
                .filter(it -> savedCartItemIds.contains(it))
                .limit(savedCartItemIds.size())
                .count() == savedCartItemIds.size();
    }
}
