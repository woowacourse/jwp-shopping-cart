package woowacourse.product.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.product.dao.ProductDao;
import woowacourse.product.dto.ProductRequest;

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

    // public List<Product> findProducts() {
    //     return productDao.findProducts();
    // }
    //
    //
    // public Product findProductById(final Long productId) {
    //     return productDao.findProductById(productId);
    // }
    //
    // public void deleteProductById(final Long productId) {
    //     productDao.delete(productId);
    // }
}
