package cart.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import cart.entiy.ProductEntity;
import org.springframework.stereotype.Repository;

@Repository
public class RdsProductRepository implements ProductRepository {
    private final ProductDao productDao;

    public RdsProductRepository(final ProductDao productDao) {
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
    public List<Product> findAll() {
        return productDao.findAll()
                .stream()
                .map(ProductEntity::toDomain)
                .collect(Collectors.toList());
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
