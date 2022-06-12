package woowacourse.product.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.product.dao.ProductDao;
import woowacourse.product.domain.Product;
import woowacourse.product.dto.ProductRequest;
import woowacourse.product.dto.ProductResponse;
import woowacourse.product.dto.ProductResponses;
import woowacourse.product.exception.InvalidProductException;

@Transactional(rollbackFor = Exception.class)
@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long addProduct(final ProductRequest productRequest) {
        return productDao.save(productRequest.toProduct());
    }

    public ProductResponses findProducts() {
        return ProductResponses.from(productDao.findAll());
    }

    public ProductResponse findProductById(final Long id) {
        final Product product = productDao.findById(id)
            .orElseThrow(() -> new InvalidProductException("해당 상품을 찾을 수 없습니다."));

        return ProductResponse.from(product);
    }

    public void deleteProductById(final Long id) {
        productDao.deleteById(id);
    }
}
