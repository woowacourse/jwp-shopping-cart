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
        MemberEntity findMemberEntity = memberDao.selectByEmailAndPassword(authMember.getEmail(), authMember.getPassword());
        CartEntity cartEntity = new CartEntity.Builder()
                .memberId(findMemberEntity.getMemberId())
                .productId(cartAddRequest.getProductId())
                .build();
        return cartDao.insert(cartEntity);
    }

    public List<CartResponse> findAllProductByMemberInfo(AuthMember authMember) {
        MemberEntity findMemberEntity = memberDao.selectByEmailAndPassword(authMember.getEmail(), authMember.getPassword());
        List<CartProductJoinEntity> cartProductJoinEntities = cartDao.selectAllProductByMemberId(findMemberEntity.getMemberId());
        return cartProductJoinEntities.stream()
                .map(cartProductJoinEntity -> new CartResponse(cartProductJoinEntity.getCartId(),
                        cartProductJoinEntity.getProductImgUrl(), cartProductJoinEntity.getProductName(),
                        cartProductJoinEntity.getProductPrice()))
                .collect(Collectors.toUnmodifiableList());
    }

    public void removeByCartId(long cartId) {
        checkCartExistBy(cartId);
        cartDao.deleteByCartId(cartId);
    }

    private void checkCartExistBy(long cartId) {
        if (cartDao.isNotExistByCartId(cartId)) {
            throw new CartProductNotFoundException("카트 ID에 해당하는 카트가 존재하지 않습니다.");
        }
    }
}
