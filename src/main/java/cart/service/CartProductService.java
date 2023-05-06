package cart.service;

import cart.dao.cart.CartDao;
import cart.domain.member.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void addCartProduct(final Long productId, final String email, final String password) {
        final Member member = memberService.find(email, password);
        cartDao.insert(member.getId(), productId);
    }

    @Transactional(readOnly = true)
    public List<Long> findAllProductIds(final String email, final String password) {
        final Member member = memberService.find(email, password);
        return cartDao.findAllProductIdByMemberId(member.getId());
    }

    @Transactional
    public void delete(final Long productId, final String email, final String password) {
        final Member member = memberService.find(email, password);
        cartDao.deleteByMemberIdAndProductId(member.getId(), productId);
    }
}
