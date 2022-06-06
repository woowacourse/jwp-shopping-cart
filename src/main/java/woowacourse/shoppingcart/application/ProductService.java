package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.repository.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findProducts() {
        return productDao.findAll();
    }

    public Product findProductById(final Long productId) {
        return productDao.findById(productId);
    }
}
