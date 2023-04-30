package cart.persistence.repository;

import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.persistence.dao.CartDao;
import cart.persistence.dao.MemberDao;
import cart.persistence.entity.CartEntity;
import cart.persistence.entity.MemberEntity;
import cart.persistence.entity.MemberProductEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberProductRepository {

    private final CartDao cartDao;
    private final MemberDao memberDao;

    public MemberProductRepository(final CartDao cartDao, final MemberDao memberDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
    }

    public long save(final String memberEmail, final Long productId) {
        final MemberEntity memberEntity = memberDao.findByEmail(memberEmail)
                .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        final CartEntity cartEntity = new CartEntity(memberEntity.getId(), productId);
        return cartDao.insert(cartEntity);
    }

    public List<MemberProductEntity> findByMemberEmail(final String memberEmail) {
        final MemberEntity memberEntity = memberDao.findByEmail(memberEmail)
                .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        return cartDao.getProductByMemberId(memberEntity.getId());
    }
}
