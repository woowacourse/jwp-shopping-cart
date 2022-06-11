package woowacourse.shoppingcart.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.entity.ProductEntity;

@Repository
public class ProductRepository {

    private final ProductDao productDao;

    public ProductRepository(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Optional<Product> findById(Long productId) {
        return productDao.findProductById(productId)
                .map(this::toProduct);
    }

    public Long save(Product product) {
        return productDao.save(toProductEntity(product));
    }

    public List<Product> findProducts() {
        return productDao.findProducts().stream()
                .map(this::toProduct)
                .collect(Collectors.toList());
    }

    private ProductEntity toProductEntity(Product product) {
        return new ProductEntity(product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl());
    }

    private Product toProduct(ProductEntity productEntity) {
        return new Product(
                productEntity.getProductId(),
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getImageUrl());
    }

    public void delete(Long productId) {
        productDao.delete(productId);
    }

    public List<Product> findProductsByCartByCustomerId(Long customerId) {
        return productDao.findByCartByCustomerId(customerId).stream()
                .map(this::toProduct)
                .collect(Collectors.toList());
    }
}
