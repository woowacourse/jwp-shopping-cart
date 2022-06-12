package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.UpdateQuantityRequest;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartService(CartItemDao cartItemDao, ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public List<CartItemResponse> findCartsByMemberId(final Long memberId) {
        final List<Long> cartIds = findCartIdsByMemberId(memberId);

        final List<Cart> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productDao.findProductById(productId);
            final int cartItemQuantity = cartItemDao.findProductQuantityIdById(cartId);
            carts.add(new Cart(cartId, product, cartItemQuantity));
        }
        return carts.stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    private List<Long> findCartIdsByMemberId(final Long memberId) {
        return cartItemDao.findIdsByMemberId(memberId);
    }

    public Long addCart(final Long productId, final Long memberId) {
        try {
            return addCartItemAndReturnId(productId, memberId);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    private Long addCartItemAndReturnId(Long productId, Long memberId) {
        if (cartItemDao.isValidCartItem(memberId, productId)) {
            cartItemDao.addQuantityCartItem(memberId, productId);
            return cartItemDao.findIdByMemberIdAndProductId(memberId, productId);
        }
        return cartItemDao.addCartItem(memberId, productId);
    }

    public void updateCartItemQuantity(final Long cartId, final UpdateQuantityRequest updateQuantityRequest) {
        cartItemDao.updateCartItemQuantity(cartId, updateQuantityRequest.getQuantity());
    }

    public void deleteCart(final Long memberId, final Long cartId) {
        validateMemberCart(cartId, memberId);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateMemberCart(final Long cartId, final Long memberId) {
        final List<Long> cartIds = findCartIdsByMemberId(memberId);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
