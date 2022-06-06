package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.application.dto.ProductResponse;
import woowacourse.shoppingcart.dao.entity.ProductEntity;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResponse> findProducts() {
        List<ProductEntity> productEntities = productDao.findProducts();
        return productEntities.stream()
                .map(productEntity -> ProductResponse.from(productEntity.toProduct()))
                .collect(Collectors.toUnmodifiableList());
    }

    public Long addProduct(final Product product) {
        return productDao.save(ProductEntity.from(product));
    }

    public ProductResponse findProductById(final Long productId) {
        ProductEntity productEntity = getProductEntity(productId);
        return ProductResponse.from(productEntity.toProduct());
    }

    private ProductEntity getProductEntity(Long productId) {
        return productDao.findProductById(productId)
                .orElseThrow(InvalidCustomerException::new);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
