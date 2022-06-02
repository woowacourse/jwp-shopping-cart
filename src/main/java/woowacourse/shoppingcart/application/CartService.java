package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;
    private final JwtTokenProvider jwtTokenProvider;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao, JwtTokenProvider jwtTokenProvider) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public List<Cart> findCarts(final HttpServletRequest request) {
        String customerName = getNameFromToken(request);
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
        final Long customerId = customerDao.findIdByUserName(customerName);
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public Long addCart(final HttpServletRequest request, final Long productId) {
        String customerName = getNameFromToken(request);
        final Long customerId = customerDao.findIdByUserName(customerName);
        try {
            return cartItemDao.addCartItem(customerId, productId);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(final HttpServletRequest request, final Long cartId) {
        String customerName = getNameFromToken(request);
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

    private String getNameFromToken(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        return jwtTokenProvider.getPayload(token);
    }
}
