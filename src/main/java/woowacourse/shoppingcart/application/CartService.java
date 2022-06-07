package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.CartDeleteServiceRequest;
import woowacourse.shoppingcart.application.dto.CartSaveServiceRequest;
import woowacourse.shoppingcart.application.dto.CartUpdateServiceRequest;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.exception.CustomerNotFoundException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.exception.ProductNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    @Transactional
    public Long add(final Long customerId, final CartSaveServiceRequest request) {
        final Customer customer = getCustomer(customerId);
        final Product product = getProduct(request.getProductId());

        return cartItemDao.addCartItem(customer.getId(), product.getId(), request.getQuantity());
    }

    public List<CartResponse> findAllByCustomerId(final Long id) {
        return cartItemDao.findAllByCustomerId(id).stream()
                .map(CartResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(final Long customerId, final CartDeleteServiceRequest request) {
        validateExistInCart(customerId, request.getCartIds());
        cartItemDao.deleteAllById(request.getCartIds());
    }

    private Customer getCustomer(final Long id) {
        return customerDao.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
    }

    private Product getProduct(final Long productId) {
        return productDao.findProductById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    private void validateExistInCart(final Long customerId, final List<Long> ids) {
        getCustomer(customerId);
        final List<Long> cartIds = findAllByCustomerId(customerId).stream()
                .mapToLong(CartResponse::getId)
                .boxed()
                .collect(Collectors.toList());

        if (!cartIds.containsAll(ids)) {
            throw new NotInCustomerCartItemException();
        }
    }

    public void updateQuantity(final Long customerId, final CartUpdateServiceRequest request) {
        getCustomer(customerId);
        final Cart cart = cartItemDao.findCartByCustomerIdAndProductId(customerId, request.getProductId())
                .orElseThrow(NotInCustomerCartItemException::new);

        cart.updateQuantity(request.getQuantity());

        cartItemDao.update(cart, customerId);
    }
}
