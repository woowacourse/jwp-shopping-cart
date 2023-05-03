package cart.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.domain.cart.Cart;
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
    public List<Cart> findByEmail(final Email email) {
        final List<CartEntity> cartEntities = cartDao.findByEmail(email.getValue());
        final List<Cart> carts = new ArrayList<>();
        for (final CartEntity cartEntity : cartEntities) {
            final Long productId = cartEntity.getProductId();
            final Optional<Product> product = productDao.findById(productId).map(ProductEntity::toDomain);
            if (product.isEmpty()) {
                throw new IllegalStateException("productId에 해당하는 product가 존재하지 않습니다.");
            }
            carts.add(new Cart(cartEntity.getCartId(), product.get()));
        }
        return carts;
    }

    @Override
    public void deleteById(final Long id) {
        cartDao.deleteById(id);
    }
}
