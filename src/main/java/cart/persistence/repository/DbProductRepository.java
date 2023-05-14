package cart.persistence.repository;

import cart.domain.product.Product;
import cart.domain.product.ProductRepository;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.ProductEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class DbProductRepository implements ProductRepository {

    private final ProductDao productDao;

    public DbProductRepository(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public long save(final Product product) {
        return this.productDao.save(mapToProductEntityForSaveFrom(product));
    }

    @Override
    public List<Product> findAll() {
        List<ProductEntity> productEntities = this.productDao.findAll();
        return productEntities.stream()
                .map(this::mapToProductFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Product productToUpdate) {
        ProductEntity productEntity = mapToEntityFromProduct(productToUpdate);
        this.productDao.update(productEntity);

    }

    @Override
    public void deleteById(long id) {
        this.productDao.deleteById(id);
    }

    @Override
    public Optional<Product> findById(Long id) {
        Optional<ProductEntity> productEntity = this.productDao.findById(id);
        return productEntity.map(this::mapToProductFromEntity);
    }

    private ProductEntity mapToProductEntityForSaveFrom(Product product) {
        return ProductEntity.createToSave(
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
    }

    private Product mapToProductFromEntity(ProductEntity productEntity) {
        return Product.create(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getImageUrl()
        );
    }

    private ProductEntity mapToEntityFromProduct(Product productToUpdate) {
        return ProductEntity.create(
                productToUpdate.getId(),
                productToUpdate.getName(),
                productToUpdate.getPrice(),
                productToUpdate.getImageUrl()
        );
    }
}
