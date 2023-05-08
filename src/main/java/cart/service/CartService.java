package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dto.AuthMember;
import cart.dto.CartAddRequest;
import cart.dto.CartResponse;
import cart.entity.CartEntity;
import cart.entity.CartProductJoinEntity;
import cart.entity.MemberEntity;
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
        List<CartProductJoinEntity> cartProductJoinEntities = cartDao.selectAllProductByMemberId(findMemberEntity.getMemberId());
        return cartProductJoinEntities.stream()
                .map(cartProductJoinEntity -> new CartResponse(cartProductJoinEntity.getCartId(),
                        cartProductJoinEntity.getProductImgUrl(), cartProductJoinEntity.getProductName(),
                        cartProductJoinEntity.getProductPrice()))
                .collect(Collectors.toUnmodifiableList());
    }

    public void removeProductByMemberInfoAndProductId(AuthMember authMember, long cartId) {
        checkMemberExistByMemberInfo(authMember);

        checkCartExistBy(cartId);
        cartDao.deleteByCartId(cartId);
    }

    private void checkMemberExistByMemberInfo(AuthMember authMember) {
        String email = authMember.getEmail();
        String password = authMember.getPassword();
        if (memberDao.isNotExistByEmailAndPassword(email, password)) {
            throw new MemberNotFoundException("사용자 인증 정보에 해당하는 사용자가 존재하지 않습니다.");
        }
    }

    private void checkCartExistBy(long cartId) {
        if (cartDao.isNotExistByCartId(cartId)) {
            throw new CartProductNotFoundException("카트 ID에 해당하는 카트가 존재하지 않습니다.");
        }
    }
}
