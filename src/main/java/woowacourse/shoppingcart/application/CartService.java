package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.CartItemsRequest;
import woowacourse.shoppingcart.dto.request.QuantityRequest;
import woowacourse.shoppingcart.dto.response.CartItemsResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public CartItemsResponse findCartsByUsername(final String username) {
        final Long customerId = customerDao.getIdByUsername(username);
        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);

        final List<CartItem> cartItems = new ArrayList<>();
        for (final Long productId : productIds) {
            final Product product = productDao.findProductById(productId);
            final int quantity = cartItemDao.findQuantity(productId, customerId);
            cartItems.add(new CartItem(product, quantity));
        }
        return CartItemsResponse.from(cartItems);
    }

    public Long addCart(final Long productId, final String username) {
        Long customerId = customerDao.getIdByUsername(username);
        Product product = productDao.findProductById(productId);
        CartItem cartItem = new CartItem(product, 1);
        try {
            return cartItemDao.addCartItem(cartItem, customerId);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void updateQuantity(Long productId, QuantityRequest quantityRequest, String username) {
        Long customerId = customerDao.getIdByUsername(username);
        Product product = productDao.findProductById(productId);
        CartItem cartItem = new CartItem(product, quantityRequest.getQuantity());
        cartItemDao.updateQuantity(cartItem, customerId);
    }

    public void deleteCart(final String username) {
        Long customerId = customerDao.getIdByUsername(username);
        cartItemDao.deleteCartItemByCustomer(customerId);
    }

    public void deleteCartItems(CartItemsRequest cartItemsRequest, String username) {
        Long customerId = customerDao.getIdByUsername(username);
        List<Long> productIds = cartItemsRequest.getProductIds();
        for (Long productId : productIds) {
            cartItemDao.deleteCartItem(productId, customerId);
        }
    }
}
