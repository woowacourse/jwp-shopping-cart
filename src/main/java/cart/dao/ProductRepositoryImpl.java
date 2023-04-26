package cart.dao;

import cart.domain.product.ImageUrl;
import cart.domain.product.Product;
import cart.domain.product.ProductCategory;
import cart.domain.product.ProductName;
import cart.domain.product.ProductPrice;
import cart.domain.product.ProductRepository;
import java.util.List;
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
    public Long save(Product product) {
        ProductEntity productEntity = new ProductEntity(product);

        return productDao.insert(productEntity);
    }
}
