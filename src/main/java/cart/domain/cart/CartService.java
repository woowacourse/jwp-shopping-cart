package cart.domain.cart;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cart.domain.exception.DbNotAffectedException;
import cart.domain.exception.EntityNotFoundException;
import cart.domain.persistence.ProductDto;
import cart.domain.persistence.dao.CartDao;
import cart.domain.persistence.dao.MemberDao;
import cart.domain.persistence.dao.ProductDao;
import cart.domain.persistence.entity.CartEntity;
import cart.domain.persistence.entity.MemberEntity;

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

    public void addProductByMember(final long productId, final String email, final String password) {
        final long memberId = getMemberIdIfRegistered(email, password);
        if (!productDao.existsById(productId)) {
            throw new EntityNotFoundException("존재하지 않는 product id입니다.");
        }
        cartDao.save(new CartEntity(memberId, productId));
    }

    public List<ProductDto> findProductsByMember(final String email, final String password) {
        final long memberId = getMemberIdIfRegistered(email, password);
        return cartDao.findAllByMemberId(memberId);
    }

    private long getMemberIdIfRegistered(final String email, final String password) {
        Optional<MemberEntity> memberEntity = memberDao.findByEmail(email);
        if (memberEntity.isEmpty() || !memberEntity.get().getPassword().equals(password)) {
            throw new EntityNotFoundException("아이디 또는 비밀번호가 잘못되었습니다.");
        }
        return memberEntity.get().getMemberId();
    }

    public void deleteByCartId(final long cartId) {
        int affected = cartDao.deleteByCartId(cartId);
        assertRowChanged(affected);
    }

    private void assertRowChanged(final int rowAffected) {
        if (rowAffected < 1) {
            throw new DbNotAffectedException("변경된 정보가 없습니다.");
        }
    }
}
