package cart.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.domain.product.Product;
import cart.domain.user.Email;
import cart.domain.user.User;
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
    public void save(final User user, final Product product) {
        final CartEntity cartEntity = new CartEntity(user.getEmail().getValue(), product.getId());
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
