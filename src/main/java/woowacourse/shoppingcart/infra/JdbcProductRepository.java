package woowacourse.shoppingcart.infra;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.infra.dao.ProductDao;
import woowacourse.shoppingcart.infra.dao.entity.ProductEntity;

@Repository
public class JdbcProductRepository implements ProductRepository {
    private final ProductDao productDao;

    public JdbcProductRepository(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Product save(final Product product) {
        final ProductEntity productEntity = productDao.save(toEntity(product));

        return toProduct(productEntity);
    }

    @Override
    public List<Product> findAllWithPage(final int page, final int size) {
        final List<ProductEntity> productEntities = productDao.findAllWithPage(page, size);

        return productEntities.stream()
                .map(this::toProduct)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> findById(final long id) {
        final Optional<ProductEntity> optionalProductEntity = productDao.findById(id);

        return toOptionalProduct(optionalProductEntity);
    }

    @Override
    public void deleteById(final long id) {
        productDao.deleteById(id);
    }

    @Override
    public long countAll() {
        return productDao.countAll();
    }

    private ProductEntity toEntity(final Product product) {
        return new ProductEntity(product.getName(), product.getPrice(), product.getImageUrl());
    }

    private Product toProduct(final ProductEntity productEntity) {
        return new Product(productEntity.getId(), productEntity.getName(), productEntity.getPrice(),
                productEntity.getImageUrl());
    }

    private Optional<Product> toOptionalProduct(final Optional<ProductEntity> optionalProductEntity) {
        if (optionalProductEntity.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(toProduct(optionalProductEntity.get()));
    }
}
