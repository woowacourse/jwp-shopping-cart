package cart.dao.product;

import cart.domain.product.Product;
import cart.domain.product.ProductRepository;
import cart.web.exception.NoSuchDataExistException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductDao productDao;

    public ProductRepositoryImpl(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> findAll() {
        final List<ProductEntity> allProductEntities = productDao.findAll();

        return allProductEntities.stream()
                .map(ProductEntity::toProduct)
                .collect(Collectors.toList());
    }

    @Override
    public Long save(final Product product) {
        final ProductEntity productEntity = new ProductEntity(
                product.getName(),
                product.getPrice().intValue(),
                product.getCategory().name(),
                product.getImageUrl()
        );

        return productDao.insert(productEntity);
    }

    @Override
    public void deleteById(final Long id) {
        validateExistProduct(id);

        productDao.deleteById(id);
    }

    @Override
    public Product update(final Product product) {
        final ProductEntity productEntity = new ProductEntity(
                product.getProductId(),
                product.getName(),
                product.getPrice().intValue(),
                product.getCategory().name(),
                product.getImageUrl()
        );

        validateExistProduct(product.getProductId());
        productDao.update(productEntity);

        return product;
    }

    @Override
    public Optional<Product> findById(final Long productId) {
        final ProductEntity productEntity = productDao.findById(productId);

        return Optional.of(productEntity.toProduct());
    }

    private void validateExistProduct(final Long id) {
        final int count = productDao.countById(id);

        if (count < 1) {
            throw new NoSuchDataExistException();
        }
    }
}
