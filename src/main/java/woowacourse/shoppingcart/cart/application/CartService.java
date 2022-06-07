package woowacourse.shoppingcart.cart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.cart.dao.CartItemDao;
import woowacourse.shoppingcart.cart.domain.Cart;
import woowacourse.shoppingcart.cart.dto.QuantityChangingRequest;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.exception.badrequest.DuplicateCartItemException;
import woowacourse.shoppingcart.exception.badrequest.NoExistCartItemException;
import woowacourse.shoppingcart.exception.notfound.NotFoundCartException;
import woowacourse.shoppingcart.product.application.ProductService;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductService productService;

    public CartService(final CartItemDao cartItemDao, final ProductService productService) {
        this.cartItemDao = cartItemDao;
        this.productService = productService;
    }

    @Transactional(readOnly = true)
    public List<Cart> findCartsBy(final Customer customer) {
        return cartItemDao.findAllByCustomerId(customer.getId());
    }

    public Long addCart(final Long productId, final Customer customer) {
        productService.findProductById(productId);
        final boolean existProduct = cartItemDao.existProduct(customer.getId(), productId);
        if (existProduct) {
            throw new DuplicateCartItemException();
        }
        return cartItemDao.addCartItem(customer.getId(), productId);
    }

    public Cart changeQuantity(final Customer customer, final Long productId, final QuantityChangingRequest request) {
        final Cart cart = fetchCartBy(customer.getId(), productId);
        final Cart updatedCart = cart.changeQuantity(request.getQuantity());
        cartItemDao.updateQuantity(updatedCart);
        return updatedCart;
    }

    private Cart fetchCartBy(final Long customerId, final Long productId) {
        try {
            return cartItemDao.findByProductAndCustomerId(productId, customerId);
        } catch (final NotFoundCartException e) {
            throw new NoExistCartItemException();
        }
    }

    public void deleteCartBy(final Customer customer, final Long productId) {
        final Cart cart = fetchCart(productId, customer.getId());
        cartItemDao.deleteCartItem(cart.getId());
    }

    private Cart fetchCart(final Long productId, final Long customerId) {
        try {
            return cartItemDao.findByProductAndCustomerId(productId, customerId);
        } catch (final NotFoundCartException e) {
            throw new NoExistCartItemException();
        }
    }
}
