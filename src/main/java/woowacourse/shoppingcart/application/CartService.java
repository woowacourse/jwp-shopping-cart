package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.ProductIdsRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

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

    public List<CartResponse> findCartProductsByCustomerId(final Long customerId) {
        checkExistById(customerId);
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);

        return cartIds.stream()
                .map(this::findProductRequestByCartId)
                .collect(Collectors.toList());
    }

    private void checkExistById(Long customerId) {
        if (!customerDao.existById(customerId)) {
            throw new InvalidCustomerException();
        }
    }

    private CartResponse findProductRequestByCartId(Long cartId) {
        Long productId = cartItemDao.findProductIdById(cartId);
        Product product = productDao.findProductById(productId);
        Integer quantity = cartItemDao.findQuantityById(cartId);
        return new CartResponse(product, quantity);
    }

    public void addCart(final Long customerId, final CartRequest cartRequest) {
        checkExistById(customerId);
        Cart cart = new Cart(customerId, cartRequest.getProductId(), cartRequest.getQuantity());
        cartItemDao.addCartItem(cart);
    }

    public void deleteCart(final Long customerId, final ProductIdsRequest productIds) {
        checkExistById(customerId);
        for (Long productId : productIds.getProductIds()) {
            cartItemDao.deleteCartItem(customerId, productId);
        }
    }

    public void updateCartQuantity(Long customerId, CartRequest cartRequest) {
        checkExistById(customerId);
        Cart cart = new Cart(customerId, cartRequest.getProductId(), cartRequest.getQuantity());
        cartItemDao.updateQuantity(cart);
    }
}
