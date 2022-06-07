package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.notfound.ProductNotFoundException;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private static final int NOTHING_DELETED = 0;

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findProducts() {
        return productDao.findProducts();
    }

    public Long addProduct(final Product product) {
        return productDao.save(product);
    }

    public Product findProductById(final Long productId) {
        try {
            return productDao.findProductById(productId);
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException();
        }
    }

    public void deleteProductById(final Long productId) {
        final int deletedCount = productDao.delete(productId);
        if (deletedCount == NOTHING_DELETED) {
            throw new ProductNotFoundException();
        }
    }
}
