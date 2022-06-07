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

    private final CustomerService customerService;
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartService(final CustomerService customerService, final CartItemDao cartItemDao, final ProductDao productDao) {
        this.customerService = customerService;
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public List<CartItemResponse> findCartsByCustomerEmail(final String email) {
        Customer customer = customerService.getCustomerByEmail(email);
        List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(customer.getId());
        return cartItems.stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toList());
    }

    public Long addCart(String email, Long productId) {
        Customer customer = customerService.getCustomerByEmail(email);

        Product product = productDao.findProductById(productId);

        return cartItemDao.addCartItem(customer.getId(), product.getId());
    }

    public void updateQuantity(String email, Long productId, int quantity) {
        Customer customer = customerService.getCustomerByEmail(email);
        cartItemDao.updateQuantity(customer.getId(), productId, quantity);
    }

    public void deleteCartItem(String email, Long productId) {
        Customer customer = customerService.getCustomerByEmail(email);
        cartItemDao.deleteCartItem(customer.getId(), productId);
    }
}
