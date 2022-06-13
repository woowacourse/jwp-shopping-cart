package woowacourse.shoppingcart.product.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.product.dao.ProductDao;
import woowacourse.shoppingcart.product.domain.Product;

@Service
@Transactional
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public List<Product> findProducts() {
        return productDao.findProducts();
    }

    @Transactional(readOnly = true)
    public Product findProductById(final Long productId) {
        return productDao.findProductById(productId);
    }

    @Transactional(readOnly = true)
    public boolean existProduct(final Long productId) {
        return productDao.existProduct(productId);
    }
}
