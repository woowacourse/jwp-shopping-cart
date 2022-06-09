package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CartItemResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CustomerService customerService;
    private final ProductService productService;
    private final CartItemDao cartItemDao;

    public CartService(final CustomerService customerService, final ProductService productService, final CartItemDao cartItemDao) {
        this.customerService = customerService;
        this.productService = productService;
        this.cartItemDao = cartItemDao;
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
        Product product = productService.findProductById(productId);

        if (cartItemDao.existsByCustomerIdAndProductId(customer.getId(), product.getId())) {
            throw new IllegalArgumentException("이미 추가된 상품입니다.");
        }

        return cartItemDao.addCartItem(CartItem.from(customer.getId(), product));
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
