package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
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

    @Transactional(readOnly = true)
    public CartResponse findCartsByCustomerId(final long customerId) {
        List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(customerId);
        return new CartResponse(cartItems.stream()
                .map(cartItem -> new ProductResponse(cartItem.getProductId(), cartItem.getProductName(),
                                cartItem.getProductPrice(), cartItem.getProductImageUrl()))
                .collect(Collectors.toList()));
    }

    public Long addCart(final Long customerId, final Long productId) {
        return cartItemDao.addCartItem(customerId, productId);
    }

    public int deleteCart(final Long customerId, final Long productId) {
        return cartItemDao.deleteCartItem(customerId, productId);
    }

    private Product findProductByCartId(Long cartId) {
        final Long productId = cartItemDao.findProductIdById(cartId).orElseThrow(InvalidCartItemException::new);
        return productDao.findProductById(productId).orElseThrow(InvalidProductException::new);
    }
}
