package cart.service;

import cart.controller.authentication.AuthInfo;
import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.domain.CartEntity;
import cart.domain.MemberEntity;
import cart.domain.ProductEntity;
import cart.dto.ResponseProductDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<ResponseProductDto> findCartProducts(final AuthInfo authInfo) {
        final MemberEntity member = findMember(authInfo);
        final List<CartEntity> cartEntities = cartDao.findAllByMemberId(member.getId());
        return cartEntities.stream()
                .map(CartEntity::getProduct)
                .map(ResponseProductDto::new)
                .collect(Collectors.toUnmodifiableList());
    }

    private MemberEntity findMember(final AuthInfo authInfo) {
        return memberDao.findByEmail(authInfo.getEmailValue());
    }

    @Transactional
    public Long insert(final AuthInfo authInfo, final Long productId) {
        final MemberEntity member = findMember(authInfo);
        final ProductEntity product = findProductById(productId);
        return cartDao.insert(new CartEntity(member, product));
    }

    private ProductEntity findProductById(final Long productId) {
        return productDao.findById(productId);
    }

    @Transactional
    public int delete(final AuthInfo authInfo, final Long productId) {
        final MemberEntity member = findMember(authInfo);
        final ProductEntity product = findProductById(productId);
        return cartDao.delete(member.getId(), product.getId());
    }
}
