package cart.product.persistence;

import cart.exception.NoSuchDataExistException;
import cart.product.domain.Product;
import cart.product.domain.ProductRepository;
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
        if (isNotExistProduct(id)) {
            throw new NoSuchDataExistException("삭제하려는 데이터가 존재하지 않습니다.");
        }

        productDao.deleteById(id);
    }

    @Override
    public Product update(final Product product) {
        if (isNotExistProduct(product.getProductId())) {
            throw new NoSuchDataExistException("수정하려는 대상이 존재하지 않습니다.");
        }

        final ProductEntity productEntity = new ProductEntity(
                product.getProductId(),
                product.getName(),
                product.getPrice().intValue(),
                product.getCategory().name(),
                product.getImageUrl()
        );

        productDao.update(productEntity);

        return product;
    }

    @Override
    public Optional<Product> findById(final Long productId) {
        if (isNotExistProduct(productId)) {
            return Optional.empty();
        }

        final ProductEntity productEntity = productDao.findById(productId);

        return Optional.of(productEntity.toProduct());
    }

    private boolean isNotExistProduct(final Long id) {
        final int count = productDao.countById(id);

        if (count < 1) {
            return true;
        }
        return false;
    }
}
