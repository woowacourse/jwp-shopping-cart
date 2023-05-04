package cart.service.cart;

import cart.dao.cart.CartDao;
import cart.dao.member.MemberDao;
import cart.dto.cart.CartRequest;
import cart.dto.member.MemberRequest;
import cart.entity.cart.Cart;
import cart.entity.cart.Count;
import cart.entity.member.Member;
import cart.exception.notfound.MemberNotFoundException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final CartDao cartDao;
    private final MemberDao memberDao;

    public CartService(final CartDao cartDao, final MemberDao memberDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
    }

    @Transactional
    public Long saveCart(final MemberRequest memberRequest, final CartRequest cartRequest) {
        Member member = memberDao.findByEmail(memberRequest.getEmail())
            .orElseThrow(MemberNotFoundException::new);

        Optional<Cart> cart = cartDao.findByMemberIdAndProductId(member, cartRequest.getProductId());

        if (cart.isEmpty()) {
            return cartDao.insertCart(new Cart(member.getId(), cartRequest.getProductId(), 1));
        }

        return update(cart.get());
    }

    private Long update(final Cart cart) {
        Cart updateCart = new Cart(
            cart.getId(),
            cart.getMemberId(),
            cart.getProductId(),
            new Count(cart.getCount() + 1));
        cartDao.updateCart(updateCart);
        return updateCart.getId();
    }
}
