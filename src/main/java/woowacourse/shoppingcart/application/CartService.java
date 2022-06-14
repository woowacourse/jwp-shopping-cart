package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.domain.cart.CartItem;
import woowacourse.shoppingcart.domain.cart.Product;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.ProductNotFoundException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartService(CartItemDao cartItemDao, ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public Cart findCartByCustomerId(long customerId) {
        List<Long> cartItemIds = cartItemDao.findIdsByCustomerId(customerId);

        final List<CartItem> cartItems = new ArrayList<>();
        for (Long cartItemId : cartItemIds) {
            Long productId = cartItemDao.findProductIdById(cartItemId);
            Product product = productDao.findProductById(productId);
            int count = cartItemDao.findCountById(cartItemId);
            cartItems.add(new CartItem(cartItemId, count, product));
        }
        return new Cart(cartItems);
    }

    public Long addCartItem(long productId, long customerId, int count) {
        checkProductExist(productId);
        Product product = productDao.findProductById(productId);
        Cart cart = findCartByCustomerId(customerId);
        try {
            cart.addCartItem(product, count);
            return cartItemDao.addCartItem(customerId, cart.getItemOf(product));
        } catch (Exception e) {
            throw new InvalidCartItemException(e.getMessage());
        }
    }

    private void checkProductExist(long productId) {
        try {
            productDao.findProductById(productId);
        } catch (InvalidProductException e) {
            throw new ProductNotFoundException();
        }
    }

    public void deleteCartItem(long customerId, long productId) {
        checkProductExist(productId);
        try {
            cartItemDao.deleteCartItem(customerId, productId);
        } catch (InvalidCartItemException e) {
            throw new ProductNotFoundException();
        }
    }

    public void updateCount(long customerId, long productId, int count) {
        checkProductExist(productId);
        Product product = productDao.findProductById(productId);
        Cart cart = findCartByCustomerId(customerId);
        cart.update(product, count);
        cartItemDao.updateCount(customerId, cart.getItemOf(product));
    }

    public void deleteCart(long customerId) {
        cartItemDao.deleteCartItemsByCustomerId(customerId);
    }
}
