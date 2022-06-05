package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dto.product.ProductResponse;
import woowacourse.shoppingcart.dto.product.ProductResponses;
import woowacourse.shoppingcart.dto.product.ProductSaveRequest;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public ProductResponses findProducts() {
        return ProductResponses.from(productDao.findProducts());
    }

    @Transactional
    public Long addProduct(final ProductSaveRequest productSaveRequest) {
        return productDao.save(productSaveRequest.toProduct());
    }

    public ProductResponse findProductById(final Long productId) {
        return ProductResponse.from(productDao.findProductById(productId));
    }

    @Transactional
    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
