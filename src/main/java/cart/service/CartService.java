package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dto.CartAddRequest;
import cart.dto.CartResponse;
import cart.dto.AuthMember;
import cart.entity.CartEntity;
import cart.entity.MemberEntity;
import cart.entity.ProductEntity;
import cart.exception.CartProductNotFoundException;
import cart.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;
    private final MemberDao memberDao;

    public CartService(final CartDao cartDao, final MemberDao memberDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
    }

    public long saveProduct(AuthMember authMember, CartAddRequest cartAddRequest) {
        checkMemberExistByMemberInfo(authMember);
        MemberEntity findMemberEntity = memberDao.selectByEmailAndPassword(authMember.getEmail(), authMember.getPassword());
        CartEntity cartEntity = new CartEntity.Builder()
                .memberId(findMemberEntity.getMemberId())
                .productId(cartAddRequest.getProductId())
                .build();
        return cartDao.insert(cartEntity);
    }

    public List<CartResponse> findAllProductByMemberInfo(AuthMember authMember) {
        checkMemberExistByMemberInfo(authMember);
        MemberEntity findMemberEntity = memberDao.selectByEmailAndPassword(authMember.getEmail(), authMember.getPassword());
        List<ProductEntity> productEntities = cartDao.selectAllProductByMemberId(findMemberEntity.getMemberId());
        return productEntities.stream()
                .map(productEntity -> new CartResponse(productEntity.getProductId(), productEntity.getImgUrl(),
                        productEntity.getName(), productEntity.getPrice()))
                .collect(Collectors.toUnmodifiableList());
    }

    public void removeProductByMemberInfoAndProductId(AuthMember authMember, long productId) {
        checkMemberExistByMemberInfo(authMember);
        MemberEntity findMemberEntity = memberDao.selectByEmailAndPassword(authMember.getEmail(), authMember.getPassword());

        checkProductExistBy(findMemberEntity.getMemberId(), productId);
        cartDao.deleteByMemberIdAndProductId(findMemberEntity.getMemberId(), productId);
    }

    private void checkMemberExistByMemberInfo(AuthMember authMember) {
        String email = authMember.getEmail();
        String password = authMember.getPassword();
        if (memberDao.isNotExistByEmailAndPassword(email, password)) {
            throw new MemberNotFoundException("사용자 인증 정보에 해당하는 사용자가 존재하지 않습니다.");
        }
    }

    private void checkProductExistBy(long memberId, long productId) {
        if (cartDao.isNotExistByMemberIdAndProductId(memberId, productId)) {
            throw new CartProductNotFoundException("해당 멤버와 상품 ID에 해당하는 상품이 카트에 존재하지 않습니다.");
        }
    }
}
