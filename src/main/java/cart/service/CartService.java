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
        MemberEntity memberEntity = memberDao.findBy(authInfo.getEmail());
        cartDao.insert(memberEntity.getId(), productId);
    }

    public List<CartEntity> searchItems(AuthInfo authInfo) {
        MemberEntity memberEntity = memberDao.findBy(authInfo.getEmail());
        return cartDao.findAll(memberEntity.getId());
    }

    public void deleteItem(int cartId) {
        cartDao.deleteById(cartId);
    }

}
