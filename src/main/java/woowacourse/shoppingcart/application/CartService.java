package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<CartItemResponse> findCartsByCustomerEmail(final String customerName) {
        Customer customer = customerDao.findIdByEmail(customerName).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(customer.getId());
        return cartItems.stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toList());
    }

    private List<Long> findCartIdsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByNickname(customerName);
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public Long addCart(String email, Long productId) {
        Customer customer = customerDao.findIdByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Product product = productDao.findProductById(productId);

        return cartItemDao.addCartItem(customer.getId(), product.getId());
    }

    public void updateQuantity(String email, Long productId, int quantity) {
        Customer customer = customerDao.findIdByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        cartItemDao.updateQuantity(customer.getId(), productId, quantity);
    }

    public void deleteCartItem(String email, Long productId) {
        Customer customer = customerDao.findIdByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        cartItemDao.deleteCartItem(customer.getId(), productId);
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
        throw new NotInCustomerCartItemException();
    }
}
