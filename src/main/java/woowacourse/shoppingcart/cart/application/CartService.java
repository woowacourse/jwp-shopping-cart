package woowacourse.shoppingcart.cart.application;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.cart.dao.CartItemDao;
import woowacourse.shoppingcart.cart.domain.Cart;
import woowacourse.shoppingcart.customer.dao.CustomerDao;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.exception.badrequest.DuplicateCartItemException;
import woowacourse.shoppingcart.exception.badrequest.NotInCustomerCartItemException;
import woowacourse.shoppingcart.product.application.ProductService;
import woowacourse.shoppingcart.product.dao.ProductDao;
import woowacourse.shoppingcart.product.domain.Product;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;
    private final ProductService productService;

    public CartService(CartItemDao cartItemDao, CustomerDao customerDao,
                       ProductDao productDao, ProductService productService) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
        this.productService = productService;
    }

    @Transactional(readOnly = true)
    public List<Cart> findCartsByCustomerName(final String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);

        final List<Cart> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productDao.findProductById(productId);
            carts.add(new Cart(cartId, product));
        }
        return carts;
    }

    private List<Long> findCartIdsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findInByNickname(customerName);
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public Long addCart(final Long productId, final Customer customer) {
        productService.findProductById(productId);
        final boolean existProduct = cartItemDao.existProduct(customer.getId(), productId);
        if (existProduct) {
            throw new DuplicateCartItemException();
        }
        return cartItemDao.addCartItem(customer.getId(), productId);
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
