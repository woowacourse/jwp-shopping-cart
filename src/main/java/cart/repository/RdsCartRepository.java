package cart.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.domain.product.Product;
import cart.domain.user.Email;
import cart.entiy.CartEntity;
import cart.entiy.ProductEntity;
import org.springframework.stereotype.Repository;

@Repository
public class RdsCartRepository implements CartRepository {

    private final CartDao cartDao;
    private final ProductDao productDao;

    public RdsCartRepository(final CartDao cartDao, final ProductDao productDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

    @Override
    public void save(final String email, final Long productId) {
        final CartEntity cartEntity = new CartEntity(email, productId);
        cartDao.insert(cartEntity);
    }

    @Override
    public List<Product> findByEmail(final Email email) {
        final List<CartEntity> cartEntities = cartDao.findByEmail(email.getValue());
        return cartEntities.stream()
                .map(CartEntity::getProductId)
                .map(productDao::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(ProductEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(final Long id) {
        cartDao.deleteById(id);
    }
}
