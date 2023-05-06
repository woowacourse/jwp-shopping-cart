package cart.service;

import cart.dao.CartDao;
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

    public CartService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Transactional(readOnly = true)
    public List<ResponseProductDto> findCartProducts(final MemberEntity member) {
        final List<CartEntity> cartEntities = cartDao.findAllByMemberId(member.getId());
        return cartEntities.stream()
                .map(CartEntity::getProduct)
                .map(ResponseProductDto::new)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public Long insert(final MemberEntity member, final ProductEntity product) {
        return cartDao.insert(new CartEntity(member, product));
    }

    @Transactional
    public int delete(final MemberEntity member, final ProductEntity product) {
        return cartDao.delete(member.getId(), product.getId());
    }
}
