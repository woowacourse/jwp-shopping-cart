package cart.service;

import cart.dao.CartDao;
import cart.dao.ItemDao;
import cart.dao.MemberDao;
import cart.entity.AuthMember;
import cart.entity.Member;
import cart.entity.PutCart;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;
    private final MemberDao memberDao;
    private final ItemDao itemDao;

    public CartService(CartDao cartDao, MemberDao memberDao, ItemDao itemDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
        this.itemDao = itemDao;
    }

    public void putItemIntoCart(Long itemId, AuthMember authMember) {
        Member member = memberDao.findByAuthMember(authMember);
        PutCart putCart = new PutCart(member.getId(), itemId);

        cartDao.save(putCart);
    }
}
