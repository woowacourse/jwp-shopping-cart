package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.member.dao.MemberDao;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.PutCartItemRequest;
import woowacourse.shoppingcart.exception.cart.NotInMemberCartException;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final MemberDao memberDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final MemberDao memberDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.memberDao = memberDao;
        this.productDao = productDao;
    }

    public List<Cart> findCartsByMemberId(final long memberId) {
        validateMember(memberId);
        return cartItemDao.findCartByMemberId(memberId);
    }

    public void addCartItem(final long memberId, final AddCartItemRequest request) {
        long productId = request.getProductId();

        validateMember(memberId);
        validateProduct(productId);

        long cartId = cartItemDao.findIdIfExistByMemberProductId(memberId, productId);

        if (cartId > 0) {
            cartItemDao.plusQuantityById(cartId);
            return;
        }

        cartItemDao.add(memberId, productId, 1);

    }

    public void updateCartItem(long memberId, long cartId, PutCartItemRequest request) {
        validateMember(memberId);
        validateMemberCart(memberId, cartId);

        cartItemDao.update(cartId, request.getQuantity());
    }

    public void deleteCart(final long memberId, final long cartId) {
        validateMember(memberId);
        validateMemberCart(memberId, cartId);

        cartItemDao.delete(cartId);
    }

    private void validateMember(final long memberId) {
        memberDao.getById(memberId);
    }

    private void validateProduct(final long productId) {
        productDao.findById(productId);
    }

    private void validateMemberCart(final long memberId, final long cartId) {
        if (cartItemDao.isValidCartIdByMemberId(memberId, cartId)) {
            return;
        }
        throw new NotInMemberCartException("해당 사용자의 유효한 장바구니가 아닙니다.");
    }

}
