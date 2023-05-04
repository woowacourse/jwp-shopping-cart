package cart.dao.product;

import cart.domain.product.*;
import cart.domain.product.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductDao productDao;

    public ProductRepositoryImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Long save(Product product) {
        ProductEntity productEntity = toEntity(product);

        return productDao.insert(productEntity);
    }

    private ProductEntity toEntity(Product product) {
        return new ProductEntity(
                product.getId(),
                product.getName(),
                product.getPrice().intValue(),
                product.getCategory().name(),
                product.getImageUrl()
        );
    }

    @Override
    public List<Product> findAll() {
        List<ProductEntity> allProductEntities = productDao.findAll();

        return allProductEntities.stream()
                .map(this::toProduct)
                .collect(Collectors.toList());
    }

    private Product toProduct(ProductEntity entity) {
        return new Product(
                ProductName.from(entity.getName()),
                ProductPrice.from(entity.getPrice()),
                ProductCategory.valueOf(entity.getCategory()),
                ImageUrl.from(entity.getImageUrl()),
                entity.getId()
        );
    }

    @Override
    public int update(Product product) {
        ProductEntity productEntity = toEntity(product);

        return productDao.update(productEntity);
    }

    @Override
    public void deleteById(Long id) {
        productDao.deleteById(id);
    }
}
