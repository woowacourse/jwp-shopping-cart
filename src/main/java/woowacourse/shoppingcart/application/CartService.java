package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.member.dao.MemberDao;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInMemberCartItemException;
import woowacourse.shoppingcart.exception.ProductNotFoundException;

import java.util.ArrayList;
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

    public List<Cart> findCartsByMemberName(final String memberName) {
        final List<Long> cartIds = findCartIdsByMemberName(memberName);

        final List<Cart> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = findProductById(productId);
            carts.add(new Cart(cartId, product));
        }
        return carts;
    }

    private List<Long> findCartIdsByMemberName(final String memberName) {
        final Long memberId = memberDao.findIdByName(memberName);
        return cartItemDao.findIdsByMemberId(memberId);
    }

    public Long addCart(final Long productId, final String memberName) {
        final Long memberId = memberDao.findIdByName(memberName);
        try {
            return cartItemDao.addCartItem(memberId, productId);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(final String memberName, final Long cartId) {
        validateMemberCart(cartId, memberName);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateMemberCart(final Long cartId, final String memberName) {
        final List<Long> cartIds = findCartIdsByMemberName(memberName);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInMemberCartItemException();
    }

    private Product findProductById(long id) {
        return productDao.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
    }
}
