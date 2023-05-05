package cart.domain.cart;

import java.util.List;

import org.springframework.stereotype.Service;

import cart.domain.exception.DbNotAffectedException;
import cart.domain.exception.EntityNotFoundException;
import cart.domain.exception.UnexpectedDomainException;
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

    public void addProductByEmail(final long productId, final String email) {
        final MemberEntity memberEntity = memberDao.findByEmail(email).orElseThrow(UnexpectedDomainException::new);
        final long memberId = memberEntity.getMemberId();
        if (!productDao.existsById(productId)) {
            throw new EntityNotFoundException("존재하지 않는 product id입니다.");
        }
        cartDao.save(new CartEntity(memberId, productId));
    }

    public List<ProductDto> findProductsByEmail(final String email) {
        final MemberEntity memberEntity = memberDao.findByEmail(email).orElseThrow(UnexpectedDomainException::new);
        final long memberId = memberEntity.getMemberId();
        return cartDao.findAllByMemberId(memberId);
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
