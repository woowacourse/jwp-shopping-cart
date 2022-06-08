package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private static final int MINIMUM_PAGE = 1;
    private static final int MINIMUM_SIZE = 0;
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long addProduct(final Product product) {
        return productDao.save(product);
    }

    public Product findProductById(final Long productId) {
        return productDao.findProductById(productId);
    }

    public List<Product> findProducts(long size, long page) {
        validateSizeAndPage(size, page);

        return productDao.findProducts(size, page);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }

    private void validateSizeAndPage(long size, long page) {
        if (size < MINIMUM_SIZE) {
            throw new IllegalArgumentException("size는 " + MINIMUM_SIZE + "이상으로 입력해야합니다.");
        }
        if (page < MINIMUM_PAGE) {
            throw new IllegalArgumentException("page는 " + MINIMUM_PAGE + "이상으로 입력해야합니다.");
        }
    }
}
