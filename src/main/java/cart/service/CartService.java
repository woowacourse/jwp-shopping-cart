package cart.service;

import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.persistence.dao.CartDao;
import cart.persistence.dao.MemberDao;
import cart.persistence.entity.CartEntity;
import cart.persistence.entity.MemberCartEntity;
import cart.persistence.entity.MemberEntity;
import cart.service.dto.ProductResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartDao cartDao;
    private final MemberDao memberDao;

    public CartService(CartDao cartDao, MemberDao memberDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
    }

    @Transactional
    public long addCart(final String memberEmail, final Long productId) {
        final MemberEntity memberEntity = getMemberEntity(memberEmail);
        final CartEntity cartEntity = new CartEntity(memberEntity.getId(), productId);
        return cartDao.insert(cartEntity);
    }

    @Transactional
    public void deleteCart(final String memberEmail, final Long productId) {
        final MemberEntity memberEntity = getMemberEntity(memberEmail);
        int deletedCount = cartDao.deleteByMemberId(memberEntity.getId(), productId);
        if (deletedCount != 1) {
            throw new GlobalException(ErrorCode.CART_INVALID_DELETE);
        }
    }

    public List<ProductResponse> getProductsByMemberEmail(final String memberEmail) {
        final MemberEntity memberEntity = getMemberEntity(memberEmail);
        final List<MemberCartEntity> memberProductEntities =  cartDao.getProductsByMemberId(memberEntity.getId());
        return memberProductEntities.stream()
            .map(this::convertToDto)
            .collect(Collectors.toUnmodifiableList());
    }

    private MemberEntity getMemberEntity(final String memberEmail) {
        return memberDao.findByEmail(memberEmail)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private ProductResponse convertToDto(final MemberCartEntity memberCartEntity) {
        return new ProductResponse(memberCartEntity.getProductId(),
            memberCartEntity.getProductName(), memberCartEntity.getProductImageUrl(),
            memberCartEntity.getProductPrice(), memberCartEntity.getProductCategory());
    }
}
