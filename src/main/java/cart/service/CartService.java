package cart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cart.exception.DbNotAffectedException;
import cart.exception.EntityMappingException;
import cart.persistence.CartProduct;
import cart.persistence.dao.CartDao;
import cart.persistence.dao.MemberDao;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.Cart;
import cart.persistence.entity.Member;

@Service
public class CartService {

    private final CartDao cartDao;
    private final MemberDao memberDao;
    private final ProductDao productDao;

    public CartService(final CartDao cartDao, final MemberDao memberDao, final ProductDao productDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
        this.productDao = productDao;
    }

    public void addProductByEmail(final long productId, final String email) {
        final Member member = memberDao.findByEmail(email).orElseThrow(IllegalArgumentException::new);
        final long memberId = member.getMemberId();
        if (!productDao.existsById(productId)) {
            throw new EntityMappingException("존재하지 않는 product id입니다.");
        }
        cartDao.save(new Cart(memberId, productId));
    }

    public List<CartProduct> findProductsByEmail(final String email) {
        final Member member = memberDao.findByEmail(email).orElseThrow(IllegalArgumentException::new);
        final long memberId = member.getMemberId();
        return cartDao.findAllByMemberId(memberId);
    }

    public void deleteByCartId(final long cartId) {
        int affected = cartDao.deleteByCartId(cartId);
        assertRowChanged(affected);
    }

    private void assertRowChanged(final int rowAffected) {
        if (rowAffected < 1) {
            throw new DbNotAffectedException();
        }
    }
}
