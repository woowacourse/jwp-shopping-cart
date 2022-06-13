package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dto.product.ProductAddRequest;
import woowacourse.shoppingcart.dto.product.ProductResponse;
import woowacourse.shoppingcart.dto.product.ProductsResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long save(final ProductAddRequest request) {
        return productDao.save(request.toProduct());
    }

    public ProductsResponse findAll() {
        return new ProductsResponse(productDao.findProducts());
    }

    public ProductResponse findById(final Long productId) {
        return ProductResponse.from(productDao.findProductById(productId));
    }

    public void deleteById(final Long productId) {
        productDao.delete(productId);
    }

}
