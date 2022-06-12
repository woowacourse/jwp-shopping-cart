package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.CartDeleteServiceRequest;
import woowacourse.shoppingcart.application.dto.CartSaveServiceRequest;
import woowacourse.shoppingcart.application.dto.CartServiceResponse;
import woowacourse.shoppingcart.application.dto.CartUpdateServiceRequest;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.exception.AlreadyInCartException;
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
        validateExistsInCart(customerId, request.getProductId());

        return cartItemDao.addCartItem(customerId, request.getProductId(), request.getQuantity());
    }

    public List<CartServiceResponse> findAllByCustomerId(final Long id) {
        return cartItemDao.findAllByCustomerId(id).stream()
                .map(CartServiceResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateQuantity(final Long customerId, final CartUpdateServiceRequest request) {
        final Cart cart = cartItemDao.findCartByCustomerIdAndProductId(customerId, request.getProductId())
                .orElseThrow(NotInCustomerCartItemException::new);

        cart.updateQuantity(request.getQuantity());

        cartItemDao.update(cart, customerId);
    }

    @Transactional
    public void delete(final Long customerId, final CartDeleteServiceRequest request) {
        validateExistInCart(customerId, request.getCartIds());
        cartItemDao.deleteAllById(request.getCartIds());
    }

    private void validateExistsInCart(final Long customerId, final Long productId) {
        if (cartItemDao.existsInCart(customerId, productId)) {
            throw new AlreadyInCartException();
        }
    }

    private void validateExistInCart(final Long customerId, final List<Long> ids) {
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);

        if (!cartIds.containsAll(ids)) {
            throw new NotInCustomerCartItemException();
        }
    }
}
