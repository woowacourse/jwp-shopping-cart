package cart.service;

import cart.dao.cart.CartDao;
import cart.domain.member.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartProductService {

    private final MemberService memberService;
    private final CartDao cartDao;

    public CartProductService(final MemberService memberService,
                              final CartDao cartDao) {
        this.memberService = memberService;
        this.cartDao = cartDao;
    }

    public void addCartProduct(final Long productId, final String email, final String password) {
        final Member member = memberService.find(email, password);
        cartDao.insert(member.getId(), productId);
    }

    public List<Long> findAllProductIds(final String email, final String password) {
        final Member member = memberService.find(email, password);
        return cartDao.findAllProductIdByMemberId(member.getId());
    }

    public void delete(final Long productId, final String email, final String password) {
        final Member member = memberService.find(email, password);
        cartDao.deleteByMemberIdAndProductId(member.getId(), productId);
    }
}
