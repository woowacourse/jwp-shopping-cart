package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.cartItem.CartItemAddRequest;
import woowacourse.shoppingcart.dto.cartItem.CartItemsResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public Long add(final Long customerId, final CartItemAddRequest request) {
        Product product = productDao.findProductById(request.getProductId());
        CartItem cartItem = new CartItem(product, request.getQuantity());
        try {
            return cartItemDao.save(customerId, cartItem);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public CartItem findById(final Long cartItemId) {
        return cartItemDao.findById(cartItemId);
    }

    public CartItemsResponse findAllByCustomerId(final Long customerId) {
        return new CartItemsResponse(cartItemDao.findAllByCustomerId(customerId));
    }

    public void updateQuantity(final Long cartItemId, final Long customerId, final int quantity) {
        CartItem cartItem = cartItemDao.findById(cartItemId);
        cartItem.changeQuantity(quantity);
        cartItemDao.updateQuantity(cartItem, customerId);
    }

    public void deleteById(final Long customerId, final Long cartItemId) {
        cartItemDao.deleteById(cartItemId, customerId);
    }

}
