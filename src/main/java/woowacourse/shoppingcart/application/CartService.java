package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.CartDeleteServiceRequest;
import woowacourse.shoppingcart.application.dto.CartSaveServiceRequest;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
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
        getCustomer(customerId);
        final List<Long> cartIds = findAllByCustomerId(customerId).stream()
                .mapToLong(CartResponse::getId)
                .boxed()
                .collect(Collectors.toList());

        if (!cartIds.containsAll(request.getCartIds())) {
            throw new NotInCustomerCartItemException();
        }

        cartItemDao.deleteAllById(request.getCartIds());
    }

//    @Transactional
//    public void delete(final Long customerId, final Long cartId) {
//        validateCustomerCart(customerId, cartId);
//        cartItemDao.deleteCartItem(cartId);
//    }

    private Customer getCustomer(final Long id) {
        return customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);
    }

    private Product getProduct(final Long productId) {
        return productDao.findProductById(productId)
                .orElseThrow(InvalidProductException::new);
    }

    private void validateCustomerCart(final Long customerId, final Long cartId) {
        getCustomer(customerId);
        if (notExistsInCart(customerId, cartId)) {
            throw new NotInCustomerCartItemException();
        }
    }

    private boolean notExistsInCart(final Long customerId, final Long cartId) {
        return findCartIdsByCustomerId(customerId).stream()
                .noneMatch(id -> id.equals(cartId));
    }

    private List<Long> findCartIdsByCustomerId(final Long id) {
        final Customer customer = getCustomer(id);

        return cartItemDao.findIdsByCustomerId(customer.getId());
    }
}
