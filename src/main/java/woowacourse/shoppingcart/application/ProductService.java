package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.product.Product;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public Long save(final Product product) {
        return productDao.save(product);
    }

    public List<Product> findAll() {
        return productDao.findProducts();
    }

    public Product findById(final Long productId) {
        return productDao.findProductById(productId);
    }

    @Transactional
    public void deleteById(final Long productId) {
        productDao.delete(productId);
    }
}
