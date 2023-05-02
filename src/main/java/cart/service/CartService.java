package cart.service;

import cart.dao.CartDao;
import cart.dao.entity.CartEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public List<Long> findProductIdsByMemberId(final Long memberId) {
        final List<CartEntity> cartEntities = cartDao.findAllByMemberId(memberId);
        return cartEntities.stream()
                .map(CartEntity::getProductId)
                .collect(Collectors.toUnmodifiableList());
    }

    public Long insert(final Long memberId, final Long productId) {
        return cartDao.insert(new CartEntity(memberId, productId));
    }

    public int delete(final Long memberId, final Long productId) {
        return cartDao.delete(memberId, productId);
    }
}
