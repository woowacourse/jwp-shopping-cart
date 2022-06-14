package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.ProductIdsRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
    }

    public List<CartResponse> findCartProductsByCustomerId(final Long customerId) {
        checkExistById(customerId);
        List<Cart> cartsByCustomerId = cartItemDao.findCartsByCustomerId(customerId);

        return cartsByCustomerId.stream().map(CartResponse::from)
                .collect(Collectors.toList());
    }

    private void checkExistById(Long customerId) {
        if (!customerDao.existById(customerId)) {
            throw new InvalidCustomerException();
        }
    }

    public void addCart(final Long customerId, final CartRequest cartRequest) {
        checkExistById(customerId);
        validateDuplicateCart(customerId, cartRequest.getProductId());
        Cart cart = Cart.of(customerId, cartRequest.getProductId(), cartRequest.getQuantity());
        cartItemDao.addCartItem(cart);
    }

    private void validateDuplicateCart(Long customerId, Long productId) {
        if (cartItemDao.existByCustomerIdAndProductId(customerId, productId)) {
            throw new IllegalArgumentException("동일한 회원이 동일한 상품을 담았습니다.");
        }
    }

    public void deleteCart(final Long customerId, final ProductIdsRequest productIds) {
        checkExistById(customerId);
        cartItemDao.deleteCartItems(customerId, productIds.getProductIds());
    }

    public void updateCartQuantity(Long customerId, CartRequest cartRequest) {
        checkExistById(customerId);
        Cart cart = Cart.of(customerId, cartRequest.getProductId(), cartRequest.getQuantity());
        cartItemDao.updateQuantity(cart);
    }
}
