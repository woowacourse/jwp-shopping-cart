package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.entity.ProductEntity;
import woowacourse.shoppingcart.exception.NotFoundProductException;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long addProduct(final ProductRequest productRequest) {
        final Product product = convertRequestToProduct(productRequest);
        return productDao.save(product);
    }

    private Product convertRequestToProduct(ProductRequest productRequest) {
        return Product.of(null, productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl(),
                productRequest.getDescription(), productRequest.getStock());
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findProducts() {
        final List<ProductEntity> products = productDao.findProducts();

        return products.stream()
                .map(ProductService::convertProductEntityToResponse)
                .collect(Collectors.toList());
    }

    public static ProductResponse convertProductEntityToResponse(ProductEntity productEntity) {
        return new ProductResponse(productEntity.getId(), productEntity.getName(),
                productEntity.getPrice(), productEntity.getImageUrl(),
                productEntity.getDescription(), productEntity.getStock());
    }

    @Transactional(readOnly = true)
    public ProductResponse findProductById(final Long productId) {
        try {
            final ProductEntity productEntity = productDao.findProductById(productId);
            return convertProductEntityToResponse(productEntity);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundProductException();
        }
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
