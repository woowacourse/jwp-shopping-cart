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
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.ProductNotFoundException;

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

    public List<CartItem> findCartItemsByCustomerId(final long customerId) {
        List<Long> cartItemIds = cartItemDao.findIdsByCustomerId(customerId);

        final List<CartItem> cartItems = new ArrayList<>();
        for (final Long cartItemId : cartItemIds) {
            final Long productId = cartItemDao.findProductIdById(cartItemId);
            final Product product = productDao.findProductById(productId);
            final int count = cartItemDao.findCountById(cartItemId);
            cartItems.add(new CartItem(cartItemId, count, product));
        }
        return cartItems;
    }

    public Long addCartItem(final Long productId, final long customerId, final int count) {
        checkProductExist(productId);
        int quantity = productDao.findProductById(productId).getQuantity();
        checkAlreadyInCart(productId, customerId);
        checkInStock(count, quantity);
        try {
            return cartItemDao.addCartItem(customerId, productId, count);
        } catch (Exception e) {
            throw new InvalidCartItemException();
        }
    }

    private void checkAlreadyInCart(Long productId, long customerId) {
        boolean isInCart = cartItemDao.findProductIdsByCustomerId(customerId).contains(productId);
        if (isInCart) {
            throw new InvalidCartItemException("이미 담겨있는 상품입니다.");
        }
    }

    private void checkProductExist(Long productId) {
        try {
            productDao.findProductById(productId);
        } catch (InvalidProductException e) {
            throw new ProductNotFoundException();
        }
    }

    public void deleteCartItem(final long customerId, final long productId) {
        checkProductExist(productId);
        try {
            cartItemDao.deleteCartItem(customerId, productId);
        } catch (InvalidCartItemException e) {
            throw new ProductNotFoundException();
        }
    }

    public void updateCount(long customerId, long productId, int count) {
        checkProductExist(productId);
        int quantity = productDao.findProductById(productId).getQuantity();
        checkInStock(count, quantity);
        cartItemDao.updateCount(customerId, productId, count);
    }

    private void checkInStock(int count, int quantity) {
        if (count > quantity) {
            throw new InvalidCartItemException("재고가 부족합니다.");
        }
    }

}
