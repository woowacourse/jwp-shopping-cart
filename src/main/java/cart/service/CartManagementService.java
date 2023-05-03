package cart.service;

import cart.dao.cart.CartDao;
import cart.domain.member.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartManagementService {

    private final MemberManagementService memberManagementService;
    private final CartDao cartDao;

    public CartManagementService(final MemberManagementService memberManagementService,
                                 final CartDao cartDao) {
        this.memberManagementService = memberManagementService;
        this.cartDao = cartDao;
    }

    public void enroll(final Long productId, final String email, final String password) {
        final Member member = memberManagementService.find(email, password);
        cartDao.save(member.getId(), productId);
    }

    public List<Long> findAll(final String email, final String password) {
        final Member member = memberManagementService.find(email, password);
        return cartDao.findAllByMemberId(member.getId());
    }

    public void delete(final Long productId, final String email, final String password) {
        final Member member = memberManagementService.find(email, password);
        cartDao.delete(member.getId(), productId);
    }
}
