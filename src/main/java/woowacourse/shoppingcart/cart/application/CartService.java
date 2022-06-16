package woowacourse.shoppingcart.cart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.cart.dao.CartItemDao;
import woowacourse.shoppingcart.cart.domain.Cart;
import woowacourse.shoppingcart.cart.domain.CartItem;
import woowacourse.shoppingcart.cart.dto.QuantityChangingRequest;
import woowacourse.shoppingcart.cart.exception.badrequest.DuplicateCartItemException;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.product.application.ProductService;

@Service
@Transactional
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductService productService;

    public CartService(final CartItemDao cartItemDao, final ProductService productService) {
        this.cartItemDao = cartItemDao;
        this.productService = productService;
    }

    @Transactional(readOnly = true)
    public Cart findCartBy(final Customer customer) {
        return cartItemDao.findCartByCustomerId(customer.getId());
    }

    public Long addCart(final Long productId, final Customer customer) {
        productService.checkProductExist(productId);
        checkCartItemExist(productId, customer);
        return cartItemDao.addCartItem(customer.getId(), productId);
    }

    private void checkCartItemExist(final Long productId, final Customer customer) {
        final boolean existCartItem = cartItemDao.existCartItem(customer.getId(), productId);
        if (existCartItem) {
            throw new DuplicateCartItemException();
        }
    }

    public CartItem changeQuantity(final Customer customer, final Long productId,
                                   final QuantityChangingRequest request) {
        final CartItem cartItem = cartItemDao.findByProductAndCustomerId(productId, customer.getId());
        final CartItem updatedCartItem = cartItem.changeQuantity(request.getQuantity());
        cartItemDao.updateQuantity(updatedCartItem);
        return updatedCartItem;
    }

    public void deleteCartBy(final Customer customer, final Long productId) {
        final CartItem cartItem = cartItemDao.findByProductAndCustomerId(productId, customer.getId());
        cartItemDao.deleteCartItem(cartItem.getId());
    }
}
