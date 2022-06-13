package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Carts;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartQuantityUpdateRequest;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartDao cartDao;
    private final ProductDao productDao;

    public CartService(final CartDao cartDao, final ProductDao productDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public List<CartResponse> findCartsById(final Long id) {
        return cartDao.findCartsByMemberId(id).stream()
                .map(CartResponse::from)
                .collect(Collectors.toList());
    }

    public Long addCart(final Long memberId, final CartRequest cartRequest) {
        final Product foundProduct = productDao.findProductById(cartRequest.getProductId());
        final Cart cart = new Cart(memberId, foundProduct, 1);

        final Carts carts = new Carts(cartDao.findCartsByMemberId(memberId));
        carts.addOrUpdate(cart);
        return saveOrUpdate(carts.getCartHave(foundProduct));
    }

    private Long saveOrUpdate(final Cart cart) {
        if (cart.isNewlyAdded()) {
            return cartDao.save(cart);
        }
        cartDao.updateQuantity(cart);
        return cart.getId();
    }

    public void deleteCart(final Long memberId, final Long cartId) {
        validateMemberCart(memberId, cartId);
        cartDao.delete(cartId);
    }

    private void validateMemberCart(final Long memberId, final Long cartId) {
        final List<Cart> cart = cartDao.findCartsByMemberId(memberId);
        final Cart foundCart = cartDao.findCartById(cartId);
        if (!cart.contains(foundCart)) {
            throw new NotInCustomerCartItemException();
        }
    }

    public void updateCartQuantity(final Long memberId, final Long cartId,
                                   final CartQuantityUpdateRequest cartQuantityUpdateRequest) {
        validateMemberCart(memberId, cartId);
        final Cart foundCart = cartDao.findCartById(cartId);
        foundCart.updateQuantity(cartQuantityUpdateRequest.getQuantity());
        cartDao.updateQuantity(foundCart);
    }
}
