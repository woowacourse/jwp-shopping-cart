package cart.service;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dto.MemberAuthRequest;
import cart.entity.CartEntity;
import cart.entity.MemberEntity;
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

    public long saveProduct(MemberAuthRequest memberAuthRequest, long productId) {
        String email = memberAuthRequest.getEmail();
        String password = memberAuthRequest.getPassword();
        checkMemberExistByMemberInfo(email, password);
        MemberEntity findMemberEntity = memberDao.selectByEmailAndPassword(email, password);
        CartEntity cartEntity = new CartEntity.Builder()
                .memberId(findMemberEntity.getMemberId())
                .productId(productId)
                .build();
        return cartDao.insert(cartEntity);
    }

    private void checkMemberExistByMemberInfo(String email, String password) {
        if (memberDao.isNotExistByEmailAndPassword(email, password)) {
            throw new MemberNotFoundException("사용자 인증 정보에 해당하는 사용자가 존재하지 않습니다.");
        }
    }
}
