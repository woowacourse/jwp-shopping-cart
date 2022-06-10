package woowacourse.shoppingcart.repository;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.entity.ProductEntity;

@Component
public class ProductRepository {
    private final ProductDao productDao;

    public ProductRepository(ProductDao productDao) {
        this.productDao = productDao;
    }

    public long save(Product product) {
        return productDao.save(product);
    }

    public Product findById(long id) {
        ProductEntity productEntity = productDao.findById(id);
        return convertEntityToDomain(productEntity);
    }

    public List<Product> findAll() {
        List<ProductEntity> productEntities = productDao.findAll();
        return productEntities.stream()
                .map(this::convertEntityToDomain)
                .collect(Collectors.toList());
    }

    public void delete(long id) {
        productDao.delete(id);
    }

    private Product convertEntityToDomain(ProductEntity productEntity) {
        return new Product(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getPrice(),
                productEntity.getStock(),
                productEntity.getImageUrl()
        );
    }
}
