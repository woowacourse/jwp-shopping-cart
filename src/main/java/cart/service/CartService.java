package cart.service;

import cart.dto.AuthInfoRequest;
import cart.repository.dao.JdbcCartDao;
import cart.repository.dao.JdbcMemberDao;
import cart.repository.dao.JdbcProductDao;
import cart.repository.entity.CartEntity;
import cart.repository.entity.MemberEntity;
import cart.repository.entity.ProductEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CartService {

    private final JdbcProductDao productDao;
    private final JdbcMemberDao memberDao;
    private final JdbcCartDao cartDao;

    public CartService(final JdbcProductDao productDao, final JdbcMemberDao memberDao, final JdbcCartDao cartDao) {
        this.productDao = productDao;
        this.memberDao = memberDao;
        this.cartDao = cartDao;
    }

    public void addProductByAuthInfo(final Long productId, final AuthInfoRequest authInfoRequest) {
        final MemberEntity memberEntity = memberDao.findByEmailAndPassword(authInfoRequest.getEmail(), authInfoRequest.getPassword());

        cartDao.save(new CartEntity(memberEntity.getId(), productId));
    }

    @Transactional(readOnly = true)
    public List<ProductEntity> findProductsByAuthInfo(final AuthInfoRequest authInfoRequest) {
        final MemberEntity memberEntity = memberDao.findByEmailAndPassword(authInfoRequest.getEmail(), authInfoRequest.getPassword());

        return cartDao.findByMemberId(memberEntity.getId()).stream()
                .map(cartEntity -> productDao.findById(cartEntity.getProductId()))
                .collect(Collectors.toList());
    }

    public void deleteProductByAuthInfo(final Long productId, final AuthInfoRequest authInfoRequest) {
        final MemberEntity memberEntity = memberDao.findByEmailAndPassword(authInfoRequest.getEmail(), authInfoRequest.getPassword());

        final int affected = cartDao.delete(memberEntity.getId(), productId);
        if (affected == 0) {
            throw new EmptyResultDataAccessException(1);
        }
    }
}
