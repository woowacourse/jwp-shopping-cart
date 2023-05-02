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

    public List<Long> findProductIdsByUserId(final Long userId) {
        final List<CartEntity> cartEntities = cartDao.findAllByUserId(userId);
        return cartEntities.stream()
                .map(CartEntity::getProductId)
                .collect(Collectors.toUnmodifiableList());
    }

    public Long insert(final Long userId, final Long productId) {
        return cartDao.insert(new CartEntity(userId, productId));
    }

    public int delete(final Long userId, final Long productId) {
        return cartDao.delete(userId, productId);
    }
}
