package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.ui.dto.ProductRequest;
import woowacourse.shoppingcart.application.dto.ProductResponse;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dao.entity.ProductEntity;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Service
@Transactional
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long save(ProductRequest productRequest) {
        Product product = productRequest.toProduct();
        return productDao.save(ProductEntity.from(product));
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long productId) {
        ProductEntity productEntity = getProductEntity(productId);
        return ProductResponse.from(productEntity.toProduct());
    }

    private ProductEntity getProductEntity(Long productId) {
        return productDao.findById(productId)
                .orElseThrow(InvalidProductException::new);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        List<ProductEntity> productEntities = productDao.findAll();
        return productEntities.stream()
                .map(productEntity -> ProductResponse.from(productEntity.toProduct()))
                .collect(Collectors.toUnmodifiableList());
    }

    public void delete(Long productId) {
        productDao.delete(productId);
    }
}
