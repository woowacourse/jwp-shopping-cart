package cart.persistence.repository;

import cart.exception.ErrorCode;
import cart.exception.ForbiddenException;
import cart.exception.GlobalException;
import cart.persistence.dao.CartDao;
import cart.persistence.dao.MemberDao;
import cart.persistence.entity.CartEntity;
import cart.persistence.entity.MemberCartEntity;
import cart.persistence.entity.MemberEntity;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Repository;

@Repository
public class MemberCartRepository {

    private final CartDao cartDao;
    private final MemberDao memberDao;

    public MemberCartRepository(final CartDao cartDao, final MemberDao memberDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
    }

    public long save(final String memberEmail, final Long productId) {
        final MemberEntity memberEntity = getMemberEntity(memberEmail);
        final CartEntity cartEntity = new CartEntity(memberEntity.getId(), productId);
        return cartDao.insert(cartEntity);
    }

    public List<MemberCartEntity> findByMemberEmail(final String memberEmail) {
        final MemberEntity memberEntity = getMemberEntity(memberEmail);
        return cartDao.getProductsByMemberId(memberEntity.getId());
    }

    public int deleteByMemberEmail(final Long targetMemberId, final String memberEmail,
                                   final Long productId) {
        final MemberEntity memberEntity = getMemberEntity(memberEmail);
        if (!Objects.equals(memberEntity.getId(), targetMemberId)) {
            throw new ForbiddenException();
        }
        return cartDao.deleteByMemberId(memberEntity.getId(), productId);
    }

    private MemberEntity getMemberEntity(final String memberEmail) {
        return memberDao.findByEmail(memberEmail)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
