package cart.dao.product;

import cart.domain.product.Product;
import cart.domain.product.ProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductDao productDao;

    public ProductRepositoryImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> findAll() {
        List<ProductEntity> allProductEntities = productDao.findAll();

        return allProductEntities.stream()
                .map(ProductEntity::toProduct)
                .collect(Collectors.toList());
    }

    @Override
    public Long save(Product product) {
        ProductEntity productEntity = new ProductEntity(
                product.getName(),
                product.getPrice().intValue(),
                product.getCategory().name(),
                product.getImageUrl()
        );

        return productDao.insert(productEntity);
    }

    @Override
    public void deleteById(Long id) {
        validateExistProduct(id);

        productDao.deleteById(id);
    }

    @Override
    public Product update(Product product) {
        ProductEntity productEntity = new ProductEntity(
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
    public Product findById(Long productId) {
        ProductEntity productEntity = productDao.findById(productId);

        return productEntity.toProduct();
    }

    private void validateExistProduct(Long id) {
        int count = productDao.countById(id);

        if (count < 1) {
            throw new NoSuchElementException();
        }
    }
}
