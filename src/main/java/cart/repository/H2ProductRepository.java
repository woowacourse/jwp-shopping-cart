package cart.repository;

import java.util.Optional;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.entiy.ProductEntity;
import org.springframework.stereotype.Repository;

@Repository
public class H2ProductRepository implements ProductRepository {
    private final ProductDao productDao;

    public H2ProductRepository(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Product save(final Product product) {
        final ProductEntity productEntity = ProductEntity.from(product);
        return productDao.insert(productEntity).toDomain();
    }

    @Override
    public Product update(final Product product) {
        final ProductEntity productEntity = productDao.update(ProductEntity.from(product));
        return productEntity.toDomain();
    }

    @Override
    public Optional<Product> findById(final Long id) {
        return productDao.findById(id).map(ProductEntity::toDomain);
    }

    @Override
    public void deleteById(final Long id) {
        productDao.deleteById(id);
    }
}
