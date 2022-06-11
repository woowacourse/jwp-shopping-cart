package woowacourse.shoppingcart.cart.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.auth.support.exception.AuthException;
import woowacourse.shoppingcart.auth.support.exception.AuthExceptionCode;
import woowacourse.shoppingcart.cart.application.dto.request.CartDeleteRequest;
import woowacourse.shoppingcart.cart.application.dto.request.CartPutRequest;
import woowacourse.shoppingcart.cart.application.dto.response.CartItemResponse;
import woowacourse.shoppingcart.cart.domain.Cart;
import woowacourse.shoppingcart.cart.support.jdbc.dao.CartItemDao;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.customer.support.jdbc.dao.CustomerDao;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.product.support.exception.ProductException;
import woowacourse.shoppingcart.product.support.exception.ProductExceptionCode;
import woowacourse.shoppingcart.product.support.jdbc.dao.ProductDao;

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

    public List<CartItemResponse> findCartByCustomerId(final long customerId) {
        final List<Cart> cartItems = cartItemDao.findAllByCustomerId(customerId);

        final List<CartItemResponse> cartItemResponses = new ArrayList<>();
        for (final Cart cart : cartItems) {
            final Product product = productDao.findById(cart.getProductId())
                    .orElseThrow(() -> new ProductException(ProductExceptionCode.NO_SUCH_PRODUCT_EXIST));
            final CartItemResponse cartItemResponse = CartItemResponse.of(product, cart);
            cartItemResponses.add(cartItemResponse);
        }
        return cartItemResponses;
    }

    @Transactional
    public CartItemResponse addCart(final long customerId, final long productId, final CartPutRequest cartPutRequest) {
        final Customer customer = customerDao.findById(customerId)
                .orElseThrow(() -> new AuthException(AuthExceptionCode.REQUIRED_AUTHORIZATION));
        final Product product = productDao.findById(productId)
                .orElseThrow(() -> new ProductException(ProductExceptionCode.NO_SUCH_PRODUCT_EXIST));
        final Cart cart = new Cart(customer.getId(), productId, cartPutRequest.getQuantity());
        cartItemDao.addCartItem(cart);
        return CartItemResponse.of(product, cart);
    }

    public boolean existsCartItem(final long customerId, final long productId) {
        return cartItemDao.existsProductByCustomer(customerId, productId);
    }

    @Transactional
    public CartItemResponse updateCart(final long customerId, final long productId,
                                       final CartPutRequest cartPutRequest) {
        deleteCart(customerId, productId);
        return addCart(customerId, productId, cartPutRequest);
    }

    @Transactional
    public void deleteCart(final long customerId, final CartDeleteRequest cartDeleteRequest) {
        cartDeleteRequest.getProductIds()
                .forEach(productId -> deleteCart(customerId, productId));
    }

    private void deleteCart(final long customerId, final long productId) {
        cartItemDao.findAllByCustomerId(customerId)
                .stream()
                .filter(it -> Objects.equals(it.getProductId(), productId))
                .findAny()
                .ifPresent(it -> cartItemDao.deleteCartItem(it.getId()));
    }
}
