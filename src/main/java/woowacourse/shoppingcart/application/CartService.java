package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.cart.CartItemUpdateRequest;
import woowacourse.shoppingcart.exception.cartItem.DuplicateCartItemBadRequestException;
import woowacourse.shoppingcart.exception.product.InvalidProductBadRequestException;
import woowacourse.shoppingcart.exception.product.InvalidQuantityBadRequestException;
import woowacourse.shoppingcart.exception.product.ShoppingCartNotFoundProductException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public List<CartItem> findCartsByCustomer(final Customer customer) {
        final List<Long> cartIds = findCartIdsByCustomerId(customer.getId());

        final List<CartItem> cartItems = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final int quantity = cartItemDao.findQuantityByProductIdAndCustomerId(productId, customer.getId());
            final Product product = productDao.findProductById(productId)
                    .orElseThrow(InvalidProductBadRequestException::new);
            cartItems.add(new CartItem(cartId, product, quantity));
        }
        return cartItems;
    }

    private List<Long> findCartIdsByCustomerId(final Long customerId) {
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public Long addCart(final Long productId, final Customer customer) {
        productDao.findProductById(productId)
                .orElseThrow(ShoppingCartNotFoundProductException::new);
        validateDuplicateCartItem(productId, customer.getId());
        try {
            return cartItemDao.addCartItem(customer.getId(), productId, 1);
        } catch (Exception e) {
            throw new InvalidProductBadRequestException();
        }
    }

    private void validateDuplicateCartItem(Long productId, Long customerId) {
        if (cartItemDao.existByProductIdAndCustomerId(productId, customerId)) {
            throw new DuplicateCartItemBadRequestException();
        }
    }

    public void deleteCart(final Customer customer, final Long productId) {
        productDao.findProductById(productId)
                .orElseThrow(ShoppingCartNotFoundProductException::new);

        cartItemDao.deleteByProductIdAndCustomerId(customer.getId(), productId);
    }

    public CartItem updateQuantity(CartItemUpdateRequest request, final Customer customer, final Long productId) {
        validateQuantity(request.getQuantity());
        Product product = productDao.findProductById(productId)
                .orElseThrow(ShoppingCartNotFoundProductException::new);

        cartItemDao.updateQuantity(customer.getId(), productId, request.getQuantity());

        int newQuantity = cartItemDao.findQuantityByProductIdAndCustomerId(productId, customer.getId());
        return new CartItem(product, newQuantity);
    }

    private void validateQuantity(int quantity) {
        if (quantity < 1) {
            throw new InvalidQuantityBadRequestException();
        }
    }
}
