package cart.service;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.domain.CartEntity;
import cart.domain.MemberEntity;
import cart.dto.AuthInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartDao cartDao;
    private final MemberDao memberDao;

    public CartService(CartDao cartDao, MemberDao memberDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
    }

    public void addItem(AuthInfo authInfo, int productId) {

        MemberEntity memberEntity = findMemberByEmail(authInfo);
        cartDao.insert(memberEntity.getId(), productId);
    }

    public List<CartEntity> searchItems(AuthInfo authInfo) {
        MemberEntity memberEntity = findMemberByEmail(authInfo);
        return cartDao.findAll(memberEntity.getId());
    }

    public void deleteItem(int cartId) {
        cartDao.deleteById(cartId);
    }

    private MemberEntity findMemberByEmail(AuthInfo authInfo) {
        return memberDao.findBy(authInfo.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 email입니다."));
    }

}
